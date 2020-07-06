<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick" center class="signupmodal">
      <el-tab-pane label="구매자" name="first">
        <customer :test="test" />
      </el-tab-pane>
      <el-tab-pane label="판매자" name="second">
        <seller />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import customer from "./Signup_buy";
import seller from "./Signup_sell";

const tabs = ["customer", "seller"];
export default {
  name: "signup",
  components: { customer, seller },
  props: {
    test: {
      type: Function
    }
  },
  data() {
    return {
      selectedTabIndex: 0,
      tabTitles: ["구매자", "판매자"],
      activeName: "first"
    };
  },
  mounted() {
    this.$router.push({ name: tabs[0] });
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
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>

<style lang="scss">
.signupmodal {
  padding: 0 10%;
}
</style>
