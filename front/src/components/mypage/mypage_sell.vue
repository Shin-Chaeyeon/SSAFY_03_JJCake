<template>
  <div class="maindiv flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <va-list fit class="mb-2">
        <va-list-label>{{ $t('프로필') }}</va-list-label>
        <template v-for="(seller, i) in sellers">
          <va-item :key="'item' + seller.id">
            <va-item-section avatar>
              <div class="avatar">
                <img :src="seller.imageUrl" :alt="seller.nickname" />
              </div>
            </va-item-section>
            <div>
              <va-item-section>
                <va-item-label class="display-3">
                  {{ market.name }}
                  <el-button
                    v-if="edit_status || id === $store.getters.auth.id"
                    type="primary"
                    icon="el-icon-edit"
                    size="mini"
                    @click="editopen()"
                    circle
                  ></el-button>
                  <el-button
                    v-if="id !== $store.getters.auth.id"
                    type="warning"
                    icon="el-icon-message"
                    size="mini"
                    @click="dialogopen"
                    circle
                  ></el-button>
                </va-item-label>
                <hr />
                <va-item-label>
                  <p>
                    <i class="el-icon-location"></i>
                    {{ market.address }}
                  </p>
                  <p>
                    <i class="el-icon-phone"></i>
                    {{ market.phoneNumber}}
                  </p>
                </va-item-label>
              </va-item-section>
            </div>
          </va-item>
          <va-list-separator v-if="i < sellers.length - 1" :key="'separator' + seller.id" />
        </template>
      </va-list>
      <br />
      <div v-if="edit_status">
        <el-tabs v-model="activeName">
          <el-tab-pane label="포트폴리오" name="first">
            <va-card>
              <div class="cards-container row d-flex wrap align--start">
                <div class="flex xs6 sm4 lg4 xl4 portimg" @click="messageOpen">
                  <va-card
                    class="portfolio"
                    image="https://www.searchpng.com/wp-content/uploads/2019/03/Add-Icon-PNG-715x715.png"
                  ></va-card>
                </div>
                <div
                  v-for="loop in portfolioList"
                  class="flex xs6 sm4 lg4 xl4 portimg"
                  :key="loop + '-5'"
                >
                  <div @click="centerDialogVisible = true">
                    <va-card class="portfolio" :image="loop.imageUrl.split(',')[0]"></va-card>
                  </div>
                </div>
              </div>
            </va-card>
          </el-tab-pane>
          <el-tab-pane label="후기" name="second" active>
            <va-card>
              <br />
              <div class="row">
                <div class="left flex xs12 md6">
                  <p class="display-1">{{avgvalue}}</p>
                  <el-rate class="avgrate" v-model="avgvalue" disabled></el-rate>
                </div>
                <div class="right flex xs12 md6">
                  5점
                  <el-progress
                    :show-text="false"
                    :text-inside="true"
                    :percentage="this.score[5]"
                    status="warning"
                  ></el-progress>4점
                  <el-progress
                    :show-text="false"
                    :text-inside="true"
                    :percentage="this.score[4]"
                    status="warning"
                  ></el-progress>3점
                  <el-progress
                    :show-text="false"
                    :text-inside="true"
                    :percentage="this.score[3]"
                    status="warning"
                  ></el-progress>2점
                  <el-progress
                    :show-text="false"
                    :text-inside="true"
                    :percentage="this.score[2]"
                    status="warning"
                  ></el-progress>1점
                  <el-progress
                    :show-text="false"
                    :text-inside="true"
                    :percentage="this.score[1]"
                    status="warning"
                  ></el-progress>
                  <br />
                </div>
              </div>
            </va-card>
            <br />
            <va-card>
              <template v-for="(review, i) in reviews">
                <div class="row flex flex-center" :key="i">
                  <el-card class="box-card">
                    <detailreview :review="review"></detailreview>
                  </el-card>
                </div>
                <br :key="i" />
              </template>
              <p class="display-4" v-if="reviews.length === 0 ">리뷰가 없습니다</p>
            </va-card>
          </el-tab-pane>
          <el-tab-pane label="정보" name="third">
            <div class="typography content">
              <va-card>
                <br />
                <div class="mb-4">
                  <p class="display-3">가게소개</p>
                  <p>{{ market.intro }}</p>
                </div>
                <hr />
                <br />
                <div class="sellcontent mb-4">
                  <p class="display-3">영업 정보</p>
                  <p class="display-5">
                    운영시간
                    <span class="text--secondary">{{market.openTime}}-{{market.closeTime}}</span>
                  </p>
                  <p class="display-5">
                    휴무일
                    <span class="text--secondary">{{market.dayOff}}</span>
                  </p>
                  <p class="display-5">
                    전화번호
                    <span class="text--secondary">{{market.phoneNumber}}</span>
                  </p>
                </div>
                <hr />
                <br />
                <div class="sellcontent mb-4">
                  <p class="display-3">위치 정보</p>
                  <p class="display-5">
                    주소
                    <span class="text--secondary">{{market.address}}</span>
                  </p>
                  <p class="display-5">
                    배달가능지역
                    <span class="text--secondary">{{market.deliveryArea}}</span>
                  </p>
                </div>
                <hr />
                <br />
                <div class="sellcontent mb-4">
                  <p class="display-3">사업자 정보</p>
                  <p class="display-5">
                    사업자등록번호
                    <span class="text--secondary">{{market.businessRegistrationNumber}}</span>
                  </p>
                </div>
              </va-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div v-else>
        <editsell :editclose="editclose"></editsell>
      </div>
    </div>
    <el-dialog :visible.sync="dialogVisible" width="50%">
      <createmessage :createdialogclose="dialogclose" :user="user" :message="message"></createmessage>
    </el-dialog>
    <el-dialog :visible.sync="portfoliodialogVisible" width="60%" center>
      <createportfolio :dialogclose="portfoliodialogclose" :use="user"></createportfolio>
    </el-dialog>
    <el-dialog :visible.sync="centerDialogVisible" width="40%" center>
      <readportfolio :dialogclose="centerdialogclose" :text="text" />
    </el-dialog>
  </div>
</template>

<script>
import data from "./data_sell.json";
import editsell from "./edit_sell";
import createmessage from "../message/createmessage";
import detailreview from "../review/detailreview";
import createportfolio from "../portfolio/create-portfolio";
import readportfolio from "../portfolio/components/read-portfolio";
import { request } from "../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    request
      .get(`/user/member/${this.id}`)
      .then(res => {
        console.log("gdgdgdgdgdgdgdgdgd");
        this.sellers = res.data.list;
        console.log("rkrpaud", res);
      })
      .catch(err => console.log(err));
    request
      .post("/v1/user/reviews", { pageIndex: 0, sellerId: this.id })
      .then(res => {
        this.reviews = res.data.list;
        for (var i = 0; i < this.reviews.length; i++) {
          this.avgvalue = this.avgvalue + this.reviews[i].stars;
          this.score[this.reviews[i].stars] =
            this.score[this.reviews[i].stars] + 1;
        }
        this.avgvalue = (this.avgvalue / this.reviews.length).toFixed(1);
        for (var i = 0; i < this.score.length; i++) {
          this.score[i] = (this.score[i] * 100) / this.reviews.length;
        }
      })
      .catch(err => console.log(err));
    request
      .post("/v1/user/markets", { sellerId: this.id })
      .then(res => {
        console.log(res.data.list[0]);
        this.market = res.data.list[0];
      })
      .catch(err => console.log(err));
    const data = {
      sellerId: this.id
    };
    request
      .post("/v1/user/portfolios", data)
      .then(res => {
        this.portfolioList = res.data.list;
        // console.log(res);
      })
      .catch(err => console.log(err));
  },
  name: "mypage_sell",
  props: {
    id: Number
  },
  data() {
    return {
      sellers: data.slice(0, 1),
      orders: data.slice(1, 6),
      reviews: "",
      tabValue: 1,
      appBanners: false,
      banners: false,
      notifications: true,
      iconSize: 20,
      activeName: "first",
      listLoops: 12,
      avgvalue: 0,
      edit_status: true,
      dialogVisible: false,
      portfoliodialogVisible: false,
      centerDialogVisible: false,
      user: "",
      text: "",
      market: {},
      portfolioList: [],
      message: {},
      score: [0, 0, 0, 0, 0, 0]
    };
  },
  methods: {
    editopen() {
      this.edit_status = false;
    },
    editclose() {
      this.edit_status = true;
    },
    dialogclose() {
      this.dialogVisible = false;
    },
    dialogopen() {
      this.message = {
        marketName: this.market.name,
        seller: this.market.sellerId
      };
      this.user = { sender: this.sellers[0].name };

      console.log("asdfasdf", this.message);
      this.dialogVisible = true;
      console.log(this.sellers);
      console.log(this.user);
    },
    centerdialogclose() {
      this.centerDialogVisible = false;
    },
    portfoliodialogclose() {
      this.portfoliodialogVisible = false;
    },
    messageOpen() {
      this.portfoliodialogVisible = true;
    }
  },
  components: {
    editsell,
    createmessage,
    detailreview,
    createportfolio,
    readportfolio
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

.review_avatar {
  display: flex;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;

  img {
    display: block;
    width: 100%;
  }
}

.markup-tables {
  .va-table {
    width: 100%;
  }
}

.va-table th {
  font-size: 1rem;
  letter-spacing: 0.6px;
  line-height: 1.2;
  font-weight: bold;
  text-transform: uppercase;
  color: #34495e;
  border-bottom: 2px solid #34495e;
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

div.left {
  width: 50%;
  float: left;
  text-align: center;
}

div.right {
  width: 50%;
  float: right;
}

.greyRule {
  background: #cecece;
  width: 450px;
  height: 1px;
  padding: 0;
  margin: 0;
}

.greyRule hr {
  display: none;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.box-card {
  width: 85%;
}

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
  padding: 0.1rem !important;
}

.reviewrate {
  padding-left: 1rem;
  padding-bottom: 0.5rem;
}

.va-item-label {
  color: #34495e;
  padding-left: 0.5rem;
}

.avgrate i {
  font-size: 4rem;

  @include media-breakpoint-down(md) {
    display: none;
  }
}

.cakeimg {
  width: 100%;
  height: 100%;
}

.left .display-1 {
  padding-left: 5rem;
  font-size: 6rem;
}

.left {
  @include media-breakpoint-down(md) {
    display: block;
  }
}

.sellcontent .display-5 {
  margin-left: 1rem;
}

hr {
  border: 0;
  height: 1px;
  background: #cccccc;
}

.editbutton {
  text-align: center;
}

.maindiv {
  z-index: 4000;
}

.el-dialog__body {
  padding: 30px 60px 20px 30px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}

.reviewcarousel {
  margin: auto;
  width: 300px;
}
</style>
