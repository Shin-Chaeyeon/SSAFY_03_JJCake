<template>
  <div>
    <div class="maheader">
      <img src="./images/header.gif" alt="header" />
    </div>
    <va-card class="tutorial">
      <div class="row flex xs12">
        <div class="flex xs12 lg6">
          <img src="./images/tutorialpage1.png" alt="tutorialpage1" />
        </div>
        <div class="flex xs12 lg6">
          <img src="./images/tutorial1.png" alt="tutorial1" />
        </div>
      </div>
      <br />
      <div class="row flex xs12">
        <div class="flex xs12 lg6">
          <img src="./images/tutorial2.png" alt="tutorial2" />
        </div>
        <div class="flex xs12 lg6">
          <img src="./images/tutorialpage2.png" alt="tutorialpage2" />
        </div>
      </div>
      <br />
      <div class="row flex xs12">
        <div class="flex xs12 lg6">
          <img src="./images/tutorialpage3.png" alt="tutorialpage3" />
        </div>
        <div class="flex xs12 lg6">
          <img src="./images/tutorial3.png" alt="tutorial3" />
        </div>
      </div>
    </va-card>
    <div class="hotportfolio">
      <el-carousel :interval="4000" type="card" height="500px">
        <el-carousel-item v-for="item in listLoops" :key="item.idportfolio">
          <img :src="item.imageUrl.split(',')[0]" style="width: 100%;" />
          <h3 class="medium"></h3>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import { request } from "../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    const data = {
      buyerId: this.$store.getters.auth.id
    };
    request
      .post("/v1/user/portfolios", data)
      .then(res => {
        this.listLoops = res.data.list;
        console.log(res);
      })
      .catch(err => console.log(err));
  },
  name: "main",
  props: {
    yes: {
      type: String
    }
  },
  data() {
    return {
      activeName: "first",
      listLoops: []
    };
  },
  components: {},
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
};
</script>

<style lang="scss">
.maheader {
  img {
    width: 100%;
  }
}

.layout {
  padding: 0;
}

.tutorial {
  margin: 20px 10%;
}

.bd {
  height: 400px;
}

.hotportfolio {
  height: 500px;
  margin: 50px 0;
  margin-bottom: 200px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 500px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.tutorial img {
  max-width: 100%;
  max-height: 100%;
}
</style>
