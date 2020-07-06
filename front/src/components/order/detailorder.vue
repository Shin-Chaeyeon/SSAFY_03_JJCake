<template>
  <div>
    <va-card id="jb-container">
      <div id="jb-header">
        <p class="display-2">{{order.title}}
        </p>
        <hr />
      </div>
      <div id="jb-content" style="text-align: center;">
        <img :src="order.imageUrl.split(',')[0]" alt=".." style="max-width: 300px;" />
        <br />
        <div class="container">
          <div class="row flex xs12 flex-center" style="margin-top: 10px;">
            <div class="flex xs3">
              <p class="display-5">맛</p>
            </div>
            {{order.cakeFlavor}}
          </div>
          <br />
          <div class="row flex xs12 flex-center" style="margin-top: 5px;">
            <div class="flex xs3">
              <p class="display-5">사이즈</p>
            </div>
            {{order.cakeSize}}
          </div>
          <br />

          <div class="row flex xs12 flex-center" style="margin-top: 5px;">
            <div class="flex xs3">
              <p class="display-5">희망 가격</p>
            </div>
            {{order.maxPrice}}
          </div>
        </div>
      </div>
      <div id="jb-sidebar">
        <p class="display-5">픽업시간, 픽업방법</p>
        <hr />
        <div class="row">
          <div class="flex xs4">{{ order.receiveDate }}</div>
          <div class="flex xs4">{{ way[order.receiveWay - 1] }}</div>
        </div>
        <br />
        <p class="display-5">케이크 모양</p>
        <hr />
        <div class="cakeshape">{{ order.cakeShape }}</div>
        <br>
        <br>
        <p class="display-5">케이크 배경색</p>
        <hr />
        <div class="cakeshape">{{ order.cakeBackgroundColor }}</div>
        <br>
        <br>
        <p class="display-5">케이크 크림색</p>
        <hr />
        <div class="cakeshape">{{ order.cakeCreamColor }}</div>
        <br>
        <br>
        <p class="display-5">원하는 문구</p>
        <hr />
        {{ order.cakeLettering }}
        <br>
        <br>
        <p class="display-5"><br> 요청사항</p>
        <hr />
        {{ order.contents }}
      </div>
    </va-card>
    <va-card style="max-width: 70rem; margin: 10px auto;">
      <div v-for="estimate in estimates" :key="'a'+estimate.idestimate" class="flex xs12">
        <div @click="test(i, $event)">
          <estimate :data="estimate"></estimate>
        </div>
        <hr />
      </div>
      <createestimate :makeestimate="makeestimate" :order="order"></createestimate>
    </va-card>
  </div>
</template>

<script>
import estimate from './estimate/estimate'
import createestimate from './estimate/create_estimate'
import { request } from '../../api/api'

export default {
  props: {
    order: Object,
  },
  created () {
    request.defaults.headers.common['X-AUTH-TOKEN'] = localStorage.getItem(
      'AccessToken',
    )
    console.log(this.order)
    request
      .post('/v1/user/estimates', { pageIndex: 0 })
      .then(res => {
        this.estimates = res.data.list
      })
      .catch(err => console.log(err))
  },
  data () {
    return {
      imageUrl: '',
      selectTaste: '맛이에요',
      selectSize: '사이즈에요',
      selectDate: '날짜입니다',
      selectTime: '시간입니다',
      selectDeliver: '픽업방법',
      selectShape: '모양선택',
      selectBackColor: '배경색',
      selectCreamColor: '크림색',
      selectTitle: '문구입니다.',
      content: '요청사항이에요',
      estimates: [],
      way: ['픽업🎁', '배달🚚'],
    }
  },
  components: {
    estimate,
    createestimate,
  },
  computed: {},
  methods: {
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/*'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('이미지 파일을 올려주세요!')
      }
      if (!isLt2M) {
        this.$message.error('2MB는 초과할 수 없습니다!')
      }
      return isJPG && isLt2M
    },
    // test (i, event) {
    //   this.estimates.push({
    //     contents: 'uahsuidfhas',
    //     price: 1000,
    //   })
    // },
    makeestimate (value) {
      this.estimates.push(value)
    },
  },
}
</script>

<style lang="scss">
#jb-container {
  width: 70rem;
  margin: 10px auto;
  // border: 1px solid #bcbcbc;
}

#jb-header {
  padding: 20px;
  margin-bottom: 20px;
  // border: 1px solid #bcbcbc;
}

#jb-content {
  width: 23rem;
  margin-bottom: 20px;
  float: left;
  // border: 1px solid #bcbcbc;
  // position: fixed;
}

#jb-sidebar {
  width: 43rem;
  padding: 20px;
  margin-bottom: 20px;
  float: right;
  // border: 1px solid #bcbcbc;
}

#jb-footer {
  clear: both;
  float: right;
  padding: 20px;
  // border: 1px solid #bcbcbc;
}

@media (max-width: 760px) {
  #jb-container {
    width: auto;
  }

  #jb-content {
    float: none;
    width: auto;
    position: initial;
  }

  #jb-sidebar {
    float: none;
    width: auto;
  }
}

.cakeshape {
  display: inline-block;
  border: 1px solid #8c939d;
  width: 70px;
  height: 70px;
  margin: 10px;
}

.makeorder {
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
  }

  .el-upload--text {
    width: 100%;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 300px;
    height: 300px;
    line-height: 300px;
    text-align: center;
  }

  .avatar {
    width: 100%;
    height: 100%;
    display: block;
  }
}

input::placeholder {
  color: black;
}
</style>
