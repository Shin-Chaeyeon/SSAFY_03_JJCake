<template>
  <div>
    <div class="createportfolio block">
      <p class="display-2 d-flex justify--center">포트폴리오작성</p>
      <br />
      <hr />
      <br />
    </div>
    <div class="row">
      <div class="left flex xs12 md5">
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/"
          list-type="picture-card"
          :on-success="handleAvatarSuccess"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
      </div>
      <div class="right flex xs12 md7">
        <el-form
          class="createportfolioform"
          :label-position="labelPosition"
          :model="form"
          label-width="120px"
        >
          <h2>제목</h2>
          <el-form-item>
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <h2>내용</h2>
          <el-form-item>
            <el-input type="textarea" cols="85" rows="14" v-model="form.contents"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <br />
    </div>
    <span slot="footer" class="dialog-footer flex-center">
      <el-button type="warning" @click="writePort">작성</el-button>
      <el-button @click="dialogclose()">취소</el-button>
    </span>
  </div>
</template>

<script>
import { request } from "../../api/api";
export default {
  name: "createporfolio",
  props: {
    dialogclose: {
      type: Function
    }
  },
  data() {
    return {
      labelPosition: "top",
      form: {
        title: "",
        contents: ""
      },
      imageUrl: [],
      dialogImageUrl: "",
      dialogVisible: false
    };
  },
  components: {},
  methods: {
    handleRemove(file, fileList) {
      this.imageUrl = fileList;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl.push(file.raw);
    },
    writePort() {
      const form = new FormData();
      for (let i = 0; i < this.imageUrl.length; i++) {
        form.append("image", this.imageUrl[i]);
      }
      form.append("title", this.form.title);
      form.append("contents", this.form.contents);
      form.append("sellerId", this.$store.getters.auth.id);
      request.defaults.headers.common["Content-Type"] = "multipart/form-data";
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .post("/v1/user/portfolio", form)
        .then(res => {
          console.log(res);
          this.dialogclose();
        })
        .catch(err => {
          console.log(err);
          this.dialogclose();
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
  position: absolute;
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
  // border: 1px solid #8c939d;
  width: 70px;
  height: 70px;
  margin: 10px;
}

.makeorder {
  .avatar-uploader .el-upload {
    // border: 1px dashed #d9d9d9;
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

  .mainimg {
    width: 300px;
    height: 300px;
    display: block;
  }

  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }

  hr {
    background-color: #dabc75 !important;
  }
}

.createportfolioform h2 {
  margin-bottom: 10px;
}
</style>
