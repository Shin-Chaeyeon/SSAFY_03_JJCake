<template>
  <div id="app" class="app">
    <router-view />
  </div>
</template>

<script>
import { request } from "../api/api";

export default {
  name: "app",
  created() {},
  data() {
    return {
      // Temporary config for 2.1.
      contextConfig: {
        gradient: true,
        shadow: "lg", // 3 states: 'sm', 'lg', undefined (no shadow).
        invertedColor: false
      }
    };
  },
  provide() {
    return {
      contextConfig: this.contextConfig
    };
  },
  watch: {
    // Temporary colors fix for 2.1.
    "contextConfig.invertedColor"(val) {
      const invertedColorClass = "va-inverted-color";
      if (val) {
        document.body.classList.add(invertedColorClass);
      } else {
        document.body.classList.remove(invertedColorClass);
      }
    }
  }
};
</script>

<style lang="scss">
@import "../sass/main.scss";

@font-face {
  font-family: "Cafe24Oneprettynight";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Oneprettynight.woff");
  font-weight: normal;
  font-style: normal;
}

.va-button {
  font-family: Cafe24Oneprettynight !important;
}

.el-tabs__item {
  padding: 0 20px;
  height: 40px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 1.3rem;
  font-weight: 700;
  color: #303133;
  position: relative;
}

body {
  height: 100%;
  background-color: #ffffff;
  font-family: Cafe24Oneprettynight !important;

  #app {
    height: 100%;
  }
}
</style>
