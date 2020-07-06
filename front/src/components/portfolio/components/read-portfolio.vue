<template>
  <div>
    <div class="block">
      <span class="demonstration"></span>
      <div class="row" @click="showUser">
        <img class="profileavatar" :src="user[0].imageUrl" alt />
        <div>
          <div class="display-3" style="padding: 10px 0 0 10px;">{{market.list[0].name}}</div>
          <div style="padding: 10px;"><p><i class="el-icon-phone"></i>  {{ market.list[0].phoneNumber}}</p></div>
        </div>
      </div>
    </div>
    <div>
      <br />
      <br />

      <el-carousel class="carouselst" indicator-position="outside">
        <el-carousel-item v-for="item in portfolioInfo.imageUrl.split(',')" :key="item">
          <img class="imgstyle" :src="item" alt />
        </el-carousel-item>
      </el-carousel>
    </div>
    <br />
    <div style="margin: 0 30px;">
      <p class="display-6"><span>{{ text }}</span></p>
    </div>
    <br />
    <br />
    <span slot="footer" class="dialog-footer flex-center" v-if="this.market.list[0].sellerId ===  $store.getters.auth.id">
      <el-button type="warning">수정</el-button>
      <el-button @click="dialogclose()">취소</el-button>
    </span>
  </div>
</template>

<script>
import { request } from "../../../api/api";
export default {
  mounted() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
  },
  props: {
    dialogclose: Function,
    text: String,
    market: Object,
    portfolioInfo: Object,
    user: Object
  },
  data() {
    return {
      images: [],
      visible: false
    };
  },
  methods: {
    showUser() {
      this.$router.push({
        name: "mypage_sell",
        params: { id: this.market.list[0].sellerId }
      });
    }
  }
};
</script>

<style>
.imgstyle {
  width: 100%;
  height: 100%;
  max-width: 18244123px;
  max-height: 123812903px;
  padding: 0 20%;
}

.profileavatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
}
</style>
