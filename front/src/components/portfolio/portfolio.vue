<template>
  <div class="wrap">
    <div class="flex xs12 pa-3 flex-center">
      <div
        v-infinite-scroll="loadMore"
        infinite-scroll-disabled="busy"
        infinite-scroll-distance="1"
        style="max-width: 70rem;"
      >
        <div style="text-align: center; font-size: 50px;">
          <b>모아보기</b>
        </div>
        <el-divider></el-divider>
        <portfolioview :listLoops="listLoops" />
      </div>
    </div>
  </div>
</template>

<script>
import portfolioview from "./components/portfolioLayout";
import { request, requestImage } from "../../api/api";

export default {
  name: "portfolio",
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    const data = {};
    request
      .post("/v1/user/portfolios", data)
      .then(res => {
        this.listLoops = res.data.list;
        this.nextIndex = res.data.nextPageIndex;
        this.busy = false;
        console.log(res);
      })
      .catch(err => console.log(err));
  },
  data() {
    return {
      listLoops: [],
      busy: true,
      nextIndex: 0
    };
  },
  components: { portfolioview },
  methods: {
    loadMore() {
      this.busy = true;
      const data = { pageIndex: this.nextIndex };
      request
        .post("/v1/user/portfolios", data)
        .then(res => {
          for (let i = 0; i < res.data.list.length; i++) {
            this.listLoops.push(res.data.list[i]);
          }
          this.nextIndex = res.data.nextPageIndex;
          if (res.data.list.length === 15) {
            this.busy = false;
          }
        })
        .catch(err => console.log(err));
    }
  }
};
</script>

<style lang="scss">
.portfolio .va-card__image {
  padding-bottom: 100%;
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;

  &:hover {
    transform: scale(1.1);
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
  }
}

.portimg {
  padding: 0.5rem !important;
}

.wrap {
  position: relative;
  width: 100%;
}

.wrap .inner {
  position: absolute;
  width: 80%;
  left: 50%;
  margin-left: -50%;
}
</style>
