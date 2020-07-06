<template>
  <div>
    {{getUser()}}
    <div class="detailreview">
      <div slot="header" class="clearfix">
        <span>
          <va-item-section avatar>
            <div class="review_avatar">
              <img :src="buyer[0].imageUrl" alt="받는이">
            </div>
            <va-item-label class="display-5" >
              <br>
              {{ buyer[0].nickname}}
            </va-item-label>
            <br>
          </va-item-section>
          <el-rate
            class="tablerate"
            v-model="review.stars"
            disabled
            show-score
            text-color="#ff9900">
          </el-rate>
        </span>
      </div>
      <hr>
      <br>
      <div class="row">
        <div class="left flex xs12 md6">
          <el-carousel class="reviewcarousel" indicator-position="outside">
            <el-carousel-item v-for="item in review.imageUrl.split(',')" :key="item">
              <img class="cakeimg" :src="item" alt="">
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="right flex xs12 md6">
          <div class="typography content" style="margin: 20px 10px 0 5px;">
            <p class="display-6">
              {{review.contents }}
            </p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { request } from "../../api/api";
export default {
  name: 'detailreview',
  props: {
    review: {
      type: Object,
    },
  },
  data () {
    return {
      buyer:'',
    }
  },
  components: {
  },
  methods: {
    getUser () {
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .get(`/user/member/${this.review.buyerId}`)
        .then(res => {
          this.buyer = res.data.list;
          console.log('rkrpaud', res);
          console.log(this.buyer);  
        })
        .catch(err => console.log(err));
    },
  },
  computed: {
  },
}
</script>

<style lang="scss">
.reviewrate i {
  font-size: 0.4em;
}

.el-carousel__item:nth-child(2n) {
  background-color: rgba(0, 0, 0, 0);
}

.el-carousel__item:nth-child(2n+1) {
  background-color: rgba(0, 0, 0, 0);
}

.detailreview hr {
  background-color: #dabc75;
  height: 2px;
}
</style>