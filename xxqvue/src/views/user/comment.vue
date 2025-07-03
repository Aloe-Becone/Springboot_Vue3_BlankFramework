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

          <div class="post-content-preview">
            {{ post.content.substring(0, 100) }}{{ post.content.length > 100 ? '...' : '' }}
          </div>

          <div v-if="post.images" class="post-media-preview">
            <el-image
                v-for="(img, index) in post.images.split(',')"
                v-if="index < 3"
                :key="index"
                :src="img"
                fit="cover"
                class="media-thumbnail"
                :preview-src-list="post.images.split(',')"
            ></el-image>
          </div>

          <div class="post-footer">
            <span class="action-item">
              <el-icon><VideoPlay /></el-icon>
              {{ post.like || 0 }}
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
        <div class="post-header">
          <span class="username">{{ $user.name }}</span>
        </div>

        <h1 class="post-title">{{ currentPost.title }}</h1>

        <div class="post-content" v-html="currentPost.content"></div>

        <div v-if="currentPost.images" class="post-media">
          <el-image
              v-for="(img, index) in currentPost.images.split(',')"
              :key="index"
              :src="img"
              fit="cover"
              class="media-item"
              :preview-src-list="currentPost.images.split(',')"
          ></el-image>
        </div>

        <div class="post-actions">
          <el-button :type="currentPost.liked ? 'danger' : ''" @click="toggleLike">
            <el-icon><VideoPlay /></el-icon>
            {{ currentPost.liked ? '已赞' : '点赞' }} ({{ currentPost.like || 0 }})
          </el-button>
        </div>

        <!-- 评论区域 -->
        <div class="comment-section">
          <h3 class="comment-title">评论 ({{ comments.length }})</h3>

          <div class="comment-input">
<!--            <el-avatar :src="currentUser.avatar" size="small" />-->
            <el-input
                v-model="commentText"
                :rows="3"
                type="textarea"
                placeholder="写下你的评论..."
                resize="none"
            />
            <div class="action-bar">
              <el-button type="primary" size="small" @click="submitComment">发表评论</el-button>
            </div>
          </div>

          <div class="comment-list">
            <div v-for="comment in comments" :key="comment.id" class="comment-item">
<!--              <el-avatar :src="getUserAvatar(comment.userId)" size="small" />-->
              <div class="comment-content">
                <div class="comment-header">
                  <span class="username">{{ $user.name }}</span>
                </div>
                <div class="comment-body">{{ comment.content }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 发帖对话框 f -->
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
              action="/api/upload"
              list-type="picture-card"
              :file-list="fileList"
              :on-success="handleUploadSuccess"
              :on-remove="handleRemove"
              :limit="9"
              multiple
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
import { ref, onMounted, inject } from 'vue'
import { ArrowLeft, ChatDotRound, VideoPlay, Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

// 当前用户信息
const $user = inject("$user")
const $request = inject('$request')

// 帖子列表数据
const posts = ref([])
const loading = ref(true)
const showPostDetail = ref(false)
const currentPost = ref(null)
const comments = ref([])
const commentText = ref('')

// 发帖对话框
const showCreateDialog = ref(false)
const newPost = ref({
  userId: $user.id,
  title: '',
  content: '',
  images: ''
})
const fileList = ref([])

// 用户数据缓存
const usersCache = ref({})

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
        newPost.value = { title: '', content: '', images: '' }
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

// 查看帖子详情
const viewPostDetail = (postId) => {
  try {
    $request.get(`/post/getPost/${postId}`).then(res => {
      if (res.data.code === '200') {
        currentPost.value = res.data.data
      } else {
        ElMessage.error(res.data.msg)
      }
    })
    showPostDetail.value = true
  } catch (error) {
    ElMessage.error('加载帖子详情失败')
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
    currentPost.value.liked = !currentPost.value.liked
    currentPost.value.like += currentPost.value.liked ? 1 : -1
    $request.get(`/post/like/${currentPost.value.id}/${currentPost.value.like}`).then(res => {
      if (res.data.code === '200') {
        currentPost.value = res.data.data
        ElMessage.success(currentPost.value.liked ? '点赞成功' : '已取消点赞')
      } else {
        ElMessage.error(res.data.msg)
      }
    })
  } catch (error) {
    ElMessage.error('操作失败')
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
        comments.value.unshift(res.data.data)
        commentText.value = ''
        ElMessage.success('评论发表成功')
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