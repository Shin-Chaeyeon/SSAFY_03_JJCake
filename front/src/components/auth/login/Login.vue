<template>
  <div>
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

    <div class="d-flex justify--center mt-3">
      <va-button type="submit" class="my-0" @click="onsubmit">{{ $t('auth.login') }}</va-button>
      <va-button type="text" class="my-0" @click="myfunction">{{ $t('auth.sign_up') }}</va-button>
      <va-button type="text" class="my-0" @click="buy">{{ $t('BUY') }}</va-button>
      <va-button type="text" class="my-0" @click="sell">{{ $t('SELL') }}</va-button>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { requestLogin, request } from "../../../api/api";
import { mapGetters } from "vuex";

export default {
  name: "login",
  props: {
    myfunction: {
      type: Function
    }
  },
  created() {},
  mounted() {},
  data() {
    return {
      email: "",
      password: "",
      keepLoggedIn: false,
      emailErrors: [],
      passwordErrors: [],
      dialog: true,
      data: ""
    };
  },
  computed: {
    formReady() {
      return !this.emailErrors.length && !this.passwordErrors.length;
    }
    // ...mapGetters([])
  },
  methods: {
    onsubmit() {
      this.emailErrors = this.email ? [] : ["Email is required"];
      this.passwordErrors = this.password ? [] : ["Password is required"];
      // if (!this.formReady) {
      //   return;
      // }
      const data = {
        email: this.email,
        password: this.password
      };
      this.requestMethod(data);
    },
    buy() {
      let data = {
        email: "qwer@gmail.com",
        password: "1234"
      };
      this.requestMethod(data);
    },
    sell() {
      let data = {
        email: "rlafkrgus963@gmail.com",
        password: "1234"
      };
      this.requestMethod(data);
    },
    requestMethod(data) {
      requestLogin
        .post("/sign_in", data)
        .then(res => {
          this.$store.commit("setToken", res.data.data);
          localStorage.setItem("AccessToken", res.data.data);
          request.defaults.headers.common[
            "X-AUTH-TOKEN"
          ] = localStorage.getItem("AccessToken");
          this.$router.push({ name: "main" });
        })
        .catch(function(err) {
          console.log(err);
          this.$store.commit("setToken", 123);
        });
    }
  }
};
</script>

<style lang="scss">
</style>
