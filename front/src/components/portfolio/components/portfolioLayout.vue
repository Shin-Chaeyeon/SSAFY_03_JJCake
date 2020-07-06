<template>
  <div class="cards-container row d-flex wrap align--start">
    <div
      v-for="loop in listLoops"
      class="flex xs6 sm4 lg4 xl4 portimg"
      :key="loop.idportfolio + '-5'"
    >
      <div @click="openModal(loop)">
        <va-card class="portfolio" :image="loop.imageUrl.split(',')[0]"></va-card>
      </div>
    </div>
    <el-dialog :visible.sync="centerDialogVisible" width="40%" center>
      <readportfolio
        :dialogclose="dialogclose"
        :text="text"
        :market="market"
        :portfolioInfo="portfolioInfo"
        :user="user"
      />
    </el-dialog>
  </div>
</template>

<script>
import readportfolio from "./read-portfolio";
import { request } from "../../../api/api";

export default {
  name: "portfolio",
  props: {
    listLoops: {
      type: Array
    }
  },
  data() {
    return {
      centerDialogVisible: false,
      text: "",
      market: {},
      sellerId: "",
      portfolioInfo: {},
      user: {}
    };
  },
  components: {
    readportfolio
  },
  methods: {
    dialogclose() {
      this.centerDialogVisible = false;
    },
    openModal(info) {
      this.text = info.contents;
      this.sellerId = info.sellerId;
      this.portfolioInfo = info;
      console.log(this.sellerId);
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .post("/v1/user/markets/", { sellerId: this.sellerId })
        .then(res => {
          // console.log(res)
          this.market = res.data;
          console.log("mar", this.market);
          request
            .get(`/user/member/${this.market.list[0].sellerId}`)
            .then(res => {
              this.user = res.data.list;
              console.log("user", this.user);
            })
            .catch(err => console.log(err));
          this.centerDialogVisible = true;
        })
        .catch(err => console.log(err));
    }
  }
};
</script>

<style lang="scss">
</style>
