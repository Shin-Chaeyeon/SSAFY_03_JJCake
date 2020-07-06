<template>
  <div class="flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <va-card class="flex flex-center" >
        <div style="padding: 0 20%;">
          <div>
            <p class="display-2 d-flex justify--center"> 개인정보 수정 </p>
            <br>
            <div class="d-flex justify--center">
              <el-upload
                class="avatar-uploader"
                action="https://jsonplaceholder.typicode.com/posts/"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </div>
            <br>
            <el-form ref="form" :model="form" label-width="120px">
              <el-form-item label="e-mail">
                <el-input v-model="form.email" disabled></el-input>
              </el-form-item>
              <el-form-item label="nickname">
                <el-input v-model="form.nickname"></el-input>
              </el-form-item>
              <el-form-item label="address">
                <el-input v-model="form.address"></el-input>
              </el-form-item>

              <el-form-item>
                <div class="editbutton">
                  <el-button type="primary" @click="onSubmit">수정</el-button>
                  <el-button @click=editclose()>취소</el-button>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </va-card>
    </div>
    <br>

  </div>
</template>

<script>
import { request } from "../../api/api";
export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    request
      .get(`/user/member/${this.$store.getters.auth.id}`)
      .then(res => {
        this.form.email = res.data.list[0].email;
        this.imageUrl = res.data.list[0].imageUrl;
      })
      .catch(err => console.log(err));
  },
  name: 'edit_buy',
  props: {
    editclose: {
      type: Function,
    },
  },
  data () {
    return {
      form: {
        email: 'nsk324@naver.com',
        password: '',
        address: this.$store.getters.auth.address,
        nickname: this.$store.getters.auth.nickname,
      },
      imageUrl: '',
    }
  },
  components: {
  },
  methods: {
    onsubmit () {
      this.emailErrors = this.email ? [] : ['Email is required']
      this.passwordErrors = this.password ? [] : ['Password is required']
      this.agreedToTermsErrors = this.agreedToTerms ? [] : ['You must agree to the terms of use to continue']
      if (!this.formReady) {
        return
      }
      this.$router.push({ name: 'dashboard' })
    },
    handleAvatarSuccess(res, file) {
      console.log(file)
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
    },
  },
  computed: {
    formReady () {
      return !(this.emailErrors.length || this.passwordErrors.length || this.agreedToTermsErrors.length)
    },
  },
}
</script>

<style lang="scss">
  .editbutton {
    margin-left: -7rem;
  }
</style>
