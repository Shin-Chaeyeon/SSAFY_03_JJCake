<template>
  <div class="flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <va-card class="flex flex-center" >
        <div style="padding: 0 20%;">
          <div>
            <p class="display-2 d-flex justify--center"> 가게정보 수정 </p>
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
              <el-form-item label="이메일">
                <el-input v-model="form.email" disabled></el-input>
              </el-form-item>
              <el-form-item label="사업자번호">
                <el-input v-model="form.business_registration_number" disabled></el-input>
              </el-form-item>
              <el-form-item label="이름">
                <el-input v-model="form.name"></el-input>
              </el-form-item>
              <el-form-item label="주소">
                <el-input v-model="form.address"></el-input>
              </el-form-item>
              <el-form-item label="전화번호">
                <el-input v-model="form.phone_number"></el-input>
              </el-form-item>
              <el-form-item label="운영시간">
                <el-col :span="11">
                  <el-time-picker placeholder="Pick a time" v-model="form.date2" style="width: 100%;"></el-time-picker>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="11">
                  <el-time-picker placeholder="Pick a time" v-model="form.date2" style="width: 100%;"></el-time-picker>
                </el-col>
              </el-form-item>
              <el-form-item label="휴무일">
                <el-checkbox-group v-model="form.day_off">
                  <el-checkbox label="월요일" name="day_off"></el-checkbox>
                  <el-checkbox label="화요일" name="day_off"></el-checkbox>
                  <el-checkbox label="수요일" name="day_off"></el-checkbox>
                  <el-checkbox label="목요일" name="day_off"></el-checkbox>
                  <el-checkbox label="금요일" name="day_off"></el-checkbox>
                  <el-checkbox label="토요일" name="day_off"></el-checkbox>
                  <el-checkbox label="일요일" name="day_off"></el-checkbox>
                </el-checkbox-group>
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

export default {
  name: 'edit_sell',
  props: {
    editclose: {
      type: Function,
    },
  },
  data () {
    return {
      form: {
        email: 'nsk324@naver.com',
        name: '',
        intro: '',
        open_time: '',
        close_time: '',
        day_off: [],
        phone_number: '',
        address: '서울광역시 어쩌구 저쩌구',
        delivery_area: '',
        business_registration_number: ''
      },
      imageUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQDEVB7OSaoNgyFkEA51Qo1tRmJnugCLNS4DzpYZGrmTBGA5iAK&usqp=CAU"
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
