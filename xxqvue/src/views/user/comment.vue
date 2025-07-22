<template>
  <div class="forum-container">
    <!-- 帖子列表 -->
    <el-card v-if="!showPostDetail" class="post-list-card">
      <template #header>
        <div class="card-header">
          <span>帖子列表</span>
          <el-button type="primary" @click="showCreateDialog = true">发新帖</el-button>
        </div>
      </template>

      <div v-if="loading" class="loading-container">
        <el-skeleton :rows="5" animated />
      </div>

      <div v-else>
        <div v-for="post in posts" :key="post.id" class="post-item" @click="viewPostDetail(post.id)">
          <div class="post-header">
            <span class="username">{{ $user.name }}</span>
          </div>

          <h3 class="post-title">{{ post.title }}</h3>

          <div v-if="post.imagesList" class="post-media-preview">
            <el-image
                v-for="(img, index) in post.imagesList"
                :key="index"
                :src="$serverURL + img"
                fit="cover"
                class="media-thumbnail"
                :preview-src-list="post.imagesList.map(img => $serverURL + img)"
            ></el-image>
          </div>

          <div class="post-footer" style="">
            <span class="action-item">
              <el-icon><Star /></el-icon>
              {{ post.likeCount }}
            </span>
            <span class="action-item">
              <el-icon><Calendar/></el-icon>
              {{ post.time }}
            </span>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 帖子详情 -->
    <el-card v-if="showPostDetail" class="post-detail-card">
      <template #header>
        <div class="card-header">
          <el-button type="text" @click="backToList">
            <el-icon><ArrowLeft /></el-icon> 返回列表
          </el-button>
          <span>帖子详情</span>
        </div>
      </template>

      <div class="post-detail">
        <div class="post-header"
             style="display: flex; justify-content: space-between; align-items: center">
          <div style="display: flex; justify-content: left; align-items: center">
            <span class="action-item">
              <el-avatar
                  style="margin-right: 10px"
                  :src= "currentPosterAvatar"
              />
              {{ $user.username }}
            </span>

          </div>
          <span class="action-item">
            <el-icon><Calendar/></el-icon>
            {{ currentPost.time }}
          </span>
        </div>

        <el-col :span="18">
          <h2 class="post-title, px-text">{{ currentPost.title }}</h2>
        </el-col>


        <div class="post-content" v-html="currentPost.content"></div>

        <div v-if="currentPost.imagesList" class="post-media-preview">
          <el-image
              v-for="(img, index) in currentPost.imagesList"
              :key="index"
              :src="$serverURL + img"
              fit="cover"
              class="media-thumbnail"
              :preview-src-list="currentPost.imagesList.map(img => $serverURL + img)"
          ></el-image>
        </div>



        <div class="post-actions">
          <el-button :type="currentPost.liked ? 'danger' : ''" @click="toggleLike">
            <el-icon><Star/></el-icon>
            {{ currentPost.liked ? ' 已赞' : ' 点赞' }} ( {{ currentPost.likeCount}} )
          </el-button>
        </div>

        <!-- 评论区域 -->
        <div class="comment-section">
          <div style="display: flex; justify-content: space-between; align-items: center">
            <h3 class="comment-title">评论 ({{ comments.length }})</h3>
            <div class="action-bar">
              <el-button type="primary" @click="submitComment">发表评论</el-button>
            </div>
          </div>

          <div class="comment-input">
            <el-input
                v-model="commentText"
                :rows="3"
                type="textarea"
                placeholder="写下你的评论..."
                resize="none"
            />
          </div>

          <div class="comment-list">
            <div v-for="comment in comments" :key="comment.id" class="comment-item">
              <el-avatar :src="comment.avatar" />
              <div class="comment-content">
                <div class="comment-header">
                  <span class="action-item">
                    <el-icon><Calendar/></el-icon>
                    {{ comment.time }}
                  </span>
                </div>
                <div class="comment-body">{{ comment.content }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 发帖对话框 -->
    <el-dialog v-model="showCreateDialog" title="发表新帖" width="800px">
      <el-form :model="newPost" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="newPost.title" placeholder="请输入标题"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <el-input v-model="newPost.content" type="textarea" :rows="8" placeholder="请输入内容"></el-input>
        </el-form-item>

        <el-form-item label="上传图片">
          <el-upload
              :action="$serverURL + 'file/upload'"
              list-type="picture-card"
              :file-list="fileList"
              :limit="3"
              multiple
              :on-success="handleUploadSuccess"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createPost">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted, inject, reactive} from 'vue'
import {ArrowLeft, Star, VideoPlay, Plus, User, Calendar} from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

// 当前用户信息
const $user = inject("$user")
const $request = inject('$request')
const $serverURL = inject('$serverURL')

// 帖子列表数据
const posts = ref([])
const loading = ref(true)
const showPostDetail = ref(false)
const currentPost = ref(null)
const currentPosterAvatar = ref('')
const comments = ref([])
const commentText = ref('')

// 发帖对话框
const showCreateDialog = ref(false)
const newPost = ref({
  userId: $user.id,
  title: '',
  content: '',
  imagesList: []
})
const fileList = ref([])

// 上传成功回调
const handleUploadSuccess = (response, file, fileList) =>
{
  console.log('上传成功，后端返回:', response)
  console.log('上传的文件:', file)
  console.log('当前文件列表:', fileList)

  if(response.code === '200')
  {
    ElMessage.success('上传成功')
    // 可以将返回的数据保存到组件data中
    newPost.value.imagesList.push(response.data)

    console.log(newPost.value.imagesList)
  }
}

// 把用户 id → 头像 URL 的映射缓存起来
const avatarCache = reactive({})

// 负责拿头像的异步函数
const fetchAvatar = async (id) => {
  if (avatarCache[id]) return avatarCache[id]        // 已缓存直接返回
  try {
    const res = await $request.get(`/user/getAvatar/${id}`)
    const url = res.data.code === '200'
        ? $serverURL + res.data.data
        : '/default-avatar.png'                       // 你的默认头像
    avatarCache[id] = url
    return url
  } catch {
    avatarCache[id] = '/default-avatar.png'
    return '/default-avatar.png'
  }
}

// 创建新帖
const createPost = async () => {
  if (!newPost.value.title.trim()) {
    ElMessage.warning('标题不能为空')
    return
  }

  try {
    console.log(newPost.value)
    await $request.post('/post/creat', newPost.value).then(res =>{
      if (res.data.code === '200') {
        showCreateDialog.value = false
        newPost.value = { title: '', content: '', imagesList: [] }
        fileList.value = []
        loadPosts()
        ElMessage.success('帖子发表成功')
      }
      else {
        // 发送失败
        ElMessage({
          message: res.data.msg,
          type: 'error',
        })
      }

    })
  } catch (error) {
    ElMessage.error('帖子发表失败')
  }
}

// 加载帖子列表
const loadPosts = async () => {
  try {
    loading.value = true
    $request.get('/post/getAll', {}).then(res => {
      if (res.data.code === '200') {
        console.log(res.data.data)
        posts.value = res.data.data
      } else {
        ElMessage.error(res.data.msg)
      }
    }).catch(err => {
      ElMessage.error('请求失败')
    })
  }
  catch (error) {
    ElMessage.error('加载帖子失败')
  } finally {
    loading.value = false
  }
}

/// 查看帖子详情
const viewPostDetail = async (postId) => {
  try {
    loading.value = true

    // 1. 拿帖子
    const postRes = await $request.get(`/post/getPost/${postId}`)
    if (postRes.data.code !== '200') {
      ElMessage.error(postRes.data.msg)
      return
    }
    currentPost.value = postRes.data.data
    currentPosterAvatar.value = await fetchAvatar(currentPost.value.userId)

    // 2. 拿评论
    const commentRes = await $request.get(`/comment/get/${postId}`)
    if (commentRes.data.code !== '200') {
      ElMessage.error(commentRes.data.msg)
      return
    }

    // 3. 批量把头像写进每条评论
    const rawComments = commentRes.data.data
    await Promise.all(
        rawComments.map(async c => {
          c.avatar = await fetchAvatar(c.userId)
          return c
        })
    )
    comments.value = rawComments
    showPostDetail.value = true
  } catch (e) {
    ElMessage.error('加载帖子详情失败')
  } finally {
    loading.value = false
  }
}

// 返回列表
const backToList = () => {
  showPostDetail.value = false
  currentPost.value = null
  comments.value = []
}

// 点赞帖子
const toggleLike = async () => {
  try {
    const newLikeStatus = !currentPost.value.liked;
    const newLikeCount = newLikeStatus
        ? currentPost.value.likeCount + 1
        : currentPost.value.likeCount - 1;

    await $request.get(`/post/like/${currentPost.value.id}/${newLikeCount}`).then(res => {
      if (res.data.code === '200') {
        currentPost.value.liked = newLikeStatus;
        currentPost.value.likeCount = newLikeCount;
        ElMessage.success(newLikeStatus ? '点赞成功' : '已取消点赞');
      } else {
        ElMessage.error(res.data.msg);
      }
    });
  } catch (error) {
    ElMessage.error('操作失败');
    console.error(error);
  }
}

// 提交评论
const submitComment = async () => {
  if (!commentText.value.trim()) {
    ElMessage.warning('评论内容不能为空')
    return
  }

  try {
    await $request.post('/comment/creat', {
      userId: $user.id,
      postId: currentPost.value.id,
      content: commentText.value
    }).then(res =>{
      if (res.data.code === '200') {
        ElMessage.success('评论发表成功')
        comments.value.unshift(res.data.data)
        commentText.value = ''
        viewPostDetail(currentPost.value.id)
      } else {
        ElMessage.error(res.data.msg)
      }
    })
  } catch (error) {
    ElMessage.error('评论发表失败')
  }
}


// 初始化加载帖子
onMounted(() => {
  loadPosts()
})
</script>

<style src="@/assets/comment.css"></style>