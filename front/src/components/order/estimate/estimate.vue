<template>
  <div>
    <va-item @click="showUser">
      <va-item-section avatar>
        <div class="avatar">
          <img :src="seller.imageUrl" :alt="data.name" />
        </div>
      </va-item-section>
      <div>
        <va-item-section>
          <va-item-label class="display-3">{{ market.name }}</va-item-label>
          <hr />
          <va-item-label>
            {{ seller.address }}
            {{ seller.email}}
          </va-item-label>
        </va-item-section>
      </div>
      <div class="estimate-price">
        <va-item-label class="display-3">
          <i class="el-icon-money"></i>
          {{data.price}} 원
        </va-item-label>
      </div>
    </va-item>
    <div class="estimate-content">{{data.contents}}</div>
  </div>
</template>

<script>
import { request } from "../../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    request
      .get(`/user/member/${this.data.sellerId}`)
      .then(res => {
        this.seller = res.data.list[0];
        console.log(res);
      })
      .catch(err => console.log(err));
    request
      .post("/v1/user/markets", { sellerId: this.data.sellerId })
      .then(res => {
        this.market = res.data.list[0];
        console.log(res);
      })
      .catch(err => console.log(err));
  },
  name: "estimate",
  props: {
    data: Object
  },
  data() {
    return {
      seller: "",
      market: ""
    };
  },
  components: {},
  methods: {
    showUser() {
      this.$router.push({
        name: "mypage_sell",
        params: { id: this.seller.idmember }
      });
    }
  }
};
</script>

<style lang="scss">
.avatar {
  display: flex;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;

  img {
    display: block;
    width: 100%;
  }
}

.estimate-price {
  position: absolute;
  right: 2.5%;
}

.estimate-content {
  margin-left: 150px;
  margin-top: 10px;
}

.va-item-section--main {
  margin-left: 15px;
}
</style>
