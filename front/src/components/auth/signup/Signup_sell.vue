<template>
  <form @submit.prevent="onsubmit()">
    <div class="contanier">
      <div class="flex xs12">
        <div class="d-flex justify--center">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" />
            <i v-else class="avatar-uploader-icon">프로필 사진 추가</i>
          </el-upload>
        </div>

        <div class="signinput">
          <va-input
            v-model="email"
            type="email"
            :label="$t('auth.email')"
            :error="!!emailErrors.length"
            :error-messages="emailErrors"
          />

          <va-input
            v-model="password"
            type="password"
            :label="$t('auth.password')"
            :error="!!passwordErrors.length"
            :error-messages="passwordErrors"
          />

          <va-input
            v-model="password2"
            type="password"
            :label="$t('Confirm password')"
            :error="!!passwordErrors.length"
            :error-messages="passwordErrors"
          />

          <va-input
            v-model="address"
            type="text"
            :label="$t('Address')"
            :error="!!passwordErrors.length"
            :error-messages="passwordErrors"
          />

          <va-input
            v-model="nickname"
            type="text"
            :label="$t('Nickname')"
            :error="!!passwordErrors.length"
            :error-messages="passwordErrors"
          />

          <va-input
            v-model="businessNumber"
            type="text"
            :label="$t('business registration number')"
            :error="!!passwordErrors.length"
            :error-messages="passwordErrors"
          />
        </div>
        <div class="d-flex justify--center mt-3">
          <va-button type="submit" class="my-0">{{ $t('auth.sign_up') }}</va-button>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import Axios from "axios";

export default {
  name: "signup",
  data() {
    return {
      email: "",
      password: "",
      address: "",
      nickname: "",
      emailErrors: [],
      passwordErrors: [],
      imageUrl: "",
      imagefile: ""
    };
  },
  methods: {
    onsubmit() {
      this.emailErrors = this.email ? [] : ["Email is required"];
      this.passwordErrors = this.password ? [] : ["Password is required"];
      this.agreedToTermsErrors = this.agreedToTerms
        ? []
        : ["You must agree to the terms of use to continue"];
      if (!this.formReady) {
        return;
      }
      Axios.defaults.headers.common["Content-Type"] = "multipart/form-data";
      const formdata = new FormData();
      formdata.append("image", this.imagefile, this.imagefile.name);
      formdata.append("address", this.address);
      formdata.append("email", this.email);
      formdata.append("nickname", this.nickname);
      formdata.append("password", this.password);
      formdata.append("admin", 2);

      Axios.post(`${this.$store.getters.url}sign_up`, formdata)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
      this.$router.push({ name: "main" });
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("Avatar picture must be JPG format!");
      }
      if (!isLt2M) {
        this.$message.error("Avatar picture size can not exceed 2MB!");
      }
      return isJPG && isLt2M;
    }
  },
  computed: {
    formReady() {
      return !(
        this.emailErrors.length ||
        this.passwordErrors.length ||
        this.agreedToTermsErrors.length
      );
    }
  }
};
</script>

<style lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 100%;
  height: 100%;
  max-width: 176px;
  max-height: 176px;
  display: block;
  position: relative;
}
</style>
