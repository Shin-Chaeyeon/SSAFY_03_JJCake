<template>
  <div class="flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <div style="padding: 0 5%;">
        <div class="detailbutton">
          <el-button type="warning" icon="el-icon-message" @click="createdialogopen(message, $event)" size="mini" circle></el-button>
          <el-button type="danger" icon="el-icon-delete" @click="remove(message)" size="mini" circle></el-button>
        </div>
        <p class="display-2 d-flex justify--center"></p>
        <div class="typography content">
          <div class="mb-4">
            <p class="display-5">
              보낸 사람 <span class="text--secondary">{{name}}</span>
            </p>
            <p class="display-5">
              받은 사람 <span class="text--secondary">{{name}}</span>
            </p>
            <p class="display-5"> 받은시간 <span class="text--secondary">{{message.sendDate}}</span></p>
          </div>
          <hr>
        </div>
        <div>
          <br>
          <p class="display-6">
            {{message.contents}}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import createmessage from './createmessage'
import { request } from "../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    request
      .get(`/user/member/${this.message.sender}`)
      .then(res => {
        this.name = res.data.list[0].nickname;
        console.log(this.name);
      })
      .catch(err => console.log(err));
    request
      .get(`/user/member/${this.message.receiver}`)
      .then(res => {
        this.receivername = res.data.list[0].nickname;
        console.log(res);
      })
      .catch(err => console.log(err));
  },
  name: 'detailmessage',
  props: {
    detaildialogclose: {
      type: Function,
    },
    message: {
      type: Object,
    },
    createdialogopen: {
      type: Function,
    },
  },
  data () {
    return {
      name: '',
      receivername: '',
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

  .notecontent .el-textarea__inner {
    height: 300px;
  }

  .typography hr {
    background-color: #dabc75;
  }

  .detailbutton {
    position: absolute;
    right: 10%;
  }
</style>
