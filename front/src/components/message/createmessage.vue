<template>
  <div class="flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <div style="padding: 0 5%;">
        <p class="createmessage display-2 d-flex justify--center">쪽지보내기</p>
        <br />
        <hr />
        <br />
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="받는 이">
            <div v-if="message.marketName">{{message.marketName}}</div>
            <div v-else>{{message.nickname}}</div>
          </el-form-item>
          <el-form-item label="제목">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="내용">
            <el-input class="notecontent" type="textarea" v-model="form.contents"></el-input>
          </el-form-item>
          <br />
          <el-form-item>
            <div class="editbutton">
              <el-button type="warning" @click="sendletter">보내기</el-button>
              <el-button @click="createdialogclose()">취소</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { request } from "../../api/api";
export default {
  created() {
    if (this.message.marketName) {
      this.form.name = this.message.marketName;
    } else {
      this.form.name = this.message.nickname;
    }
  },
  name: "createmessage",
  props: {
    createdialogclose: {
      type: Function
    },
    message: {
      type: Object
    }
  },
  data() {
    return {
      form: {
        name: "",
        title: "",
        contents: ""
      }
    };
  },
  components: {},
  methods: {
    sendletter() {
      const createmessageclose = this.createdialogclose;
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .post("/v1/user/message", {
          sender: this.$store.getters.auth.id,
          receiver: this.message.sender,
          title: this.form.title,
          contents: this.form.contents
        })
        .then(function(res) {
          createmessageclose();
        })
        .catch(err => console.log(err));
    }
  },
  computed: {
    formReady() {
      return !(this.titleErrors.length || this.contentsErrors.length);
    }
  }
};
</script>

<style lang="scss">
.editbutton {
  margin-left: -7rem;
}

.notecontent .el-textarea__inner {
  height: 300px;
}

.createmessage hr {
  background-color: #f9f2df6e;
}
</style>
