<template>
  <va-card id="jb-container">
    <div id="jb-header">
      <h1>주문서 작성</h1>
      <span class="demo-input-label">제목</span>
      <el-input type="text" :rows="1" placeholder="Please input" v-model="Title"></el-input>
      <hr />
    </div>
    <div id="jb-content">
      <el-upload
        class="avatar-uploader makeorder"
        action="https://jsonplaceholder.typicode.com/posts/"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        style="text-align: center;"
      >
        <img v-if="imageUrl" :src="imageUrl" style="max-width: 20rem;" />
        <i v-else class="avatar-uploader-icon">케이크사진 선택</i>
      </el-upload>
      <br />
      <div class="container">
        <div class="row flex xs12">
          <div class="flex xs3" style="margin-top: 5px;">
            <b>맛</b>
          </div>
          <el-select v-model="selectTaste" slot="prepend">
            <el-option label="생크림" value="1"></el-option>
            <el-option label="고구마" value="2"></el-option>
            <el-option label="초코" value="3"></el-option>
            <el-option label="기타" value="4"></el-option>
          </el-select>
        </div>
        <br />
        <div class="row flex xs12">
          <div class="flex xs3" style="margin-top: 5px;">
            <b>사이즈</b>
          </div>
          <el-select v-model="selectSize" slot="prepend">
            <el-option label="지름(15cm)" value="1"></el-option>
            <el-option label="지름(18cm)" value="2"></el-option>
            <el-option label="기타" value="3"></el-option>
          </el-select>
        </div>
        <br />
        <div class="row flex xs12">
          <div class="flex xs3" style="margin-top: 5px;">
            <b>최대 가격</b>
          </div>
          <div class="flex xs9">
            <el-input type="Number" placeholder="Please input" v-model="selectPrice"></el-input>
          </div>
        </div>
      </div>
    </div>
    <div id="jb-sidebar">
      <h2>픽업방법, 시간을 선택해주세요</h2>
      <hr />
      <div class="row">
        <div class="flex xs4">
          <el-date-picker v-model="selectDate" type="date" placeholder="날짜 선택"></el-date-picker>
        </div>
        <div class="flex xs1"></div>
        <div class="flex xs4">
          <el-select v-model="selectDeliver" slot="prepend" placeholder="픽업 방법 선택">
            <el-option label="픽업" value="1"></el-option>
            <el-option label="배달" value="2"></el-option>
          </el-select>
        </div>
      </div>
      <br />
      <h2 @click="test">케이크 모양</h2>
      <hr />
      <el-radio-group v-model="selectShape">
        <el-radio-button v-for="i in selectShapeList" class="cakeshape" :label="i" :key="i+1"></el-radio-button>
      </el-radio-group>
      <h2>케이크 배경색</h2>
      <hr />
      <el-radio-group v-model="selectBackColor">
        <el-radio-button v-for="i in selectColorList" class="cakeshape" :label="i" :key="i+1"></el-radio-button>
      </el-radio-group>
      <h2>케이크 크림색</h2>
      <hr />
      <el-radio-group v-model="selectCreamColor">
        <el-radio-button v-for="i in selectColorList" class="cakeshape" :label="i" :key="i+1"></el-radio-button>
      </el-radio-group>
      <h2>원하시는 문구를 적어주세요</h2>
      <hr />
      <el-input type="textarea" :rows="2" placeholder="Please input" v-model="selectTitle"></el-input>
      <h2>요청사항을 적어주세요</h2>
      <hr />
      <el-input type="textarea" :rows="4" placeholder="Please input" v-model="Content"></el-input>
    </div>
    <div id="jb-footer">
      <el-button type="warning" @click="createorder">주문서 작성</el-button>
    </div>
  </va-card>
</template>

<script>
$(document).ready(function() {
  $(".layout").scroll(function() {
    if ($(".layout").scrollTop() > 128) {
      $("#jb-content").css({ position: "fixed", marginTop: "-128px" });
    } else {
      $("#jb-content").css({ position: "initial", marginTop: "0px" });
    }
  });
});

import { request } from "../../api/api";

export default {
  data() {
    return {
      imageUrl: "",
      Title: "",
      selectTaste: "맛을 선택해주세요",
      selectSize: "사이즈를 선택해주세요",
      selectDate: "",
      selectTime: "",
      selectDeliver: "",
      selectShape: "",
      selectBackColor: "",
      selectCreamColor: "",
      selectTitle: "",
      selectPrice: "",
      Content: "",
      selectShapeList: ["사각", "원형", "하트", "기타"],
      selectColorList: [
        "흰색",
        "노랑색",
        "분홍색",
        "빨강색",
        "주황",
        "살구색",
        "하늘색",
        "파란색",
        "남색",
        "갈색",
        "검정색",
        "연두색",
        "초록색",
        "민트색"
      ],
      iamge: "",
      Flavor: ["", "생크림", "고구마", "초코", "기타"],
      Size: ["", "지름 15cm", "지금 18cm", "기타"]
    };
  },
  components: {},
  computed() {
    Content: {
      return selectShapeList.find(selectShape);
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.image = file.raw;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type.indexOf("image");
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (isJPG === -1) {
        this.$message.error("이미지 사진을 골라주세요");
      }
      if (!isLt2M) {
        this.$message.error("2메가는 못 넘어요 ~");
      }
      return isJPG && isLt2M;
    },
    test() {
      console.log(this.selectShapeList.findIndex(e => e == this.selectShape));
    },
    createorder() {
      const form = new FormData();
      form.append("image", this.image);
      form.append("title", this.Title);
      form.append("contents", this.Content);
      form.append("status", 1);
      form.append("maxPrice", this.selectPrice);
      form.append("cakeFlavor", this.Flavor[this.selectTaste]);
      form.append("cakeSize", this.Size[this.selectSize]);
      form.append("cakeShape", this.selectShape);
      form.append("cakeLettering", this.selectTitle);
      form.append("cakeBackgroundColor", this.selectBackColor);
      form.append("cakeBoarderPosition", "test");
      form.append("cakeCreamColor", this.selectCreamColor);
      form.append("dueDate", "2020-06-10 00:00:00");
      form.append("receiveDate", "2020-06-10 00:00:00");
      form.append("receiveWay", this.selectDeliver);
      form.append("radius", 10);
      form.append("buyerId", 12);
      // form.append("sellerId", 0);

      request.defaults.headers.common["Content-Type"] = "multipart/form-data";
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );

      request
        .post("/v1/user/order", form)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
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

  h2 {
    margin-top: 10px;
  }
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
    position: initial !important;
  }

  #jb-sidebar {
    float: none;
    width: auto;
  }
}

.cakeshape .el-radio-button__inner {
  display: inline-block;
  border: 1px solid #8c939d;
  width: 70px;
  height: 70px;
  margin: 10px;
  padding-top: 28px;
  border-radius: 4px !important;
}

.cakeshape .el-radio-button__inner:hover {
  color: #e6a23c;
}

.is-active .el-radio-button__inner {
  color: #ffffff;
  background-color: #e6a23c !important;
  border-color: #e6a23c !important;
  -webkit-box-shadow: -1px 0 0 0 #e6a23c !important;
  box-shadow: -1px 0 0 0 #e6a23c !important;
}

.is-active .el-radio-button__inner:hover {
  color: #ffffff !important;
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
