<template>
  <div class="auth-layout row align-content--center">
    <div class="flex xs12 pa-3 flex-center">
      <img class="logoimg" src="./images/logo.png" alt="logo" />
    </div>
    <!-- <va-advanced-color-picker class="my-1" v-model="$themes.primary" /> -->
    <div class="flex xs12 pa-3">
      <div class="d-flex justify--center">
        <va-card class="auth-layout__card">
          <div class="d-flex justify--center" v-if="activeName==='first'">
            <b>LOGIN</b>
            <br />
          </div>
          <login :myfunction="dialogOpen" />
          <!-- <el-button type="text" @click="dialogVisible = true">click to open the Dialog</el-button> -->
        </va-card>
      </div>
    </div>
    <el-dialog title="SignUp" :visible.sync="dialogVisible" width="60%" :before-close="handleClose">
      <signup :test="test" />
    </el-dialog>
  </div>
</template>

<script>
import VaIconVuestic from "../../iconset/VaIconVuestic";
import login from "./login/Login";
import signup from "./signup/Signup";

const tabs = ["login", "signup"];

export default {
  name: "AuthLayout",
  components: { VaIconVuestic, login, signup },
  data() {
    return {
      selectedTabIndex: 0,
      tabTitles: ["login", "Signup"],
      activeName: "first",
      dialogVisible: false
    };
  },
  mounted() {
    this.$themes.primary = "#BF916F";
  },
  computed: {
    tabIndex: {
      set(tabIndex) {
        this.$router.push({ name: tabs[tabIndex] });
      },
      get() {
        return tabs.indexOf(this.$route.name);
      }
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm("회원가입을 취소하시겠습니까?")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    test() {
      this.dialogVisible = false;
    },
    dialogOpen() {
      this.dialogVisible = true;
    }
  }
};
</script>

<style lang="scss">
.auth-layout {
  min-height: 100vh;

  &__card {
    width: 100%;
    max-width: 600px;
    opacity: 0.7;
  }

  &__options {
    @include media-breakpoint-down(xs) {
      flex-direction: column;
    }
  }
}

.logoimg {
  max-width: 20%;

  @include media-breakpoint-down(md) {
    max-width: 50%;
  }
}

.auth-layout::after {
  background-image: url("./images/background.jpg");
  top: 0;
  left: 0;
  position: absolute;
  background-size: cover;
  opacity: 0.5 !important;
  filter: alpha(opacity=50);
  z-index: -1;
  content: "";
  width: 100%;
  height: 100%;
}

.el-tabs__item.is-active {
  color: #dabc75;
}

.el-tabs__item:hover {
  color: #af9559;
  cursor: pointer;
}

.el-tabs__active-bar {
  background-color: #dabc75;
}
</style>
