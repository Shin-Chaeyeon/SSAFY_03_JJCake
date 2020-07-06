<template>
  <div class="flex xs12 flex-center">
    <div style="max-width: 70rem;">
      <va-list fit class="mb-2">
        <va-list-label>{{ $t('프로필') }}</va-list-label>
        <template v-for="(customer, i) in customers">
          <va-item :key="'item' + customer.id">
            <va-item-section avatar>
              <div class="avatar">
                <img :src="customer.imageUrl" :alt="customer.name" />
              </div>
            </va-item-section>
            <div @click="editopen()">
              <va-item-section>
                <va-item-label class="display-3">
                  {{ customer.nickname }}
                  <el-button
                    v-if="edit_status"
                    type="primary"
                    icon="el-icon-edit"
                    size="mini"
                    @click="editopen()"
                    circle
                  ></el-button>
                </va-item-label>
                <hr />
                <va-item-label>
                  {{ customer.address }}
                  {{ customer.email}}
                </va-item-label>
              </va-item-section>
            </div>
          </va-item>
          <va-list-separator v-if="i < customers.length - 1" :key="'separator' + customer.id" />
        </template>
      </va-list>
      <br />
      <div v-if="edit_status">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="주문내역" name="first" active>
            <div class="markup-tables">
              <va-card>
                <table class="va-table va-table--striped va-table--hoverable">
                  <thead>
                    <tr>
                      <th>주문서 명</th>
                      <th>가격</th>
                      <th>주문일</th>
                      <th>수령예정일</th>
                      <th>상태</th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr v-for="order in orders" :key="order.idorder">
                      <td @click="showUser(order)">{{ order.title }}</td>
                      <td>{{ order.maxPrice }}</td>
                      <td>{{ order.createDate }}</td>
                      <td>{{ order.receiveDate }}</td>

                      <td>
                        <va-badge :color="getStatusColor(order.status)">{{status[order.status]}}</va-badge>
                        <el-button v-if="order.status ===5" type="danger" size="mini" round @click="createreviewdialogopen(order, $event)" > 후기작성하기</el-button>
                      </td>
                      <va-list-separator
                        v-if="i < orders.length - 1"
                        :key="'separator' + order.idorder"
                      />
                    </tr>
                  </tbody>
                </table>
              </va-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="내 후기" name="second">
            <div class="markup-tables">
              <va-card>
                <table class="va-table va-table--striped va-table--hoverable">
                  <thead>
                    <tr>
                      <th>사진</th>
                      <th>내용</th>
                      <th>별점</th>
                      <th>판매자</th>
                      <th></th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr v-for="review in reviews" :key="review.idreview">
                      <td>
                        <el-image style="width: 100px; height: 100px;" :src="review.image_url"></el-image>
                      </td>
                      <td @click="detailreviewdialogopen(review, $event)">{{ review.contents }}</td>
                      <td>
                        <el-rate
                          class="tablerate"
                          v-model="review.score"
                          disabled
                          show-score
                          text-color="#ff9900"
                        ></el-rate>
                      </td>
                      <td>{{ review.sender }}</td>
                      <td>
                        <el-button
                          type="danger"
                          icon="el-icon-delete"
                          size="mini"
                          @click="remove(review)"
                          circle
                        ></el-button>
                      </td>
                      <va-list-separator
                        v-if="i < orders.length - 1"
                        :key="'separator' + order.idorder"
                      />
                    </tr>
                  </tbody>
                </table>
                  <p class="display-4" v-if="reviews.length === 0 ">
                  <br> 작성한 리뷰가 없습니다 </p>
              </va-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div v-else>
        <editbuy :editclose="editclose"></editbuy>
      </div>
    </div>
    <el-dialog
      class="detailreview"
      :visible.sync="detailreviewdialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <detailreview :detailreviewdialogclose="detailreviewdialogclose" :review="selectreview"></detailreview>
    </el-dialog>
    <el-dialog :visible.sync="createreviewdialogVisible" width="60%" center>
      <createreview :dialogclose="createreviewdialogclose" :order="selectorder"></createreview>
    </el-dialog>
  </div>
</template>

<script>
import data from "./data_buy.json";
import editbuy from "./edit_buy.vue";
import detailreview from "../review/detailreview.vue";
import createreview from '../review/create-review';
import { request } from "../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    this.customers = [this.$store.getters.auth];
    request
      .post("/v1/user/orders", {
        pageIndex: 0,
        buyerId: this.$store.getters.auth.id
      })
      .then(res => {
        this.orders = res.data.list;
      })
      .catch(err => console.log(err));
    request
      .post("/v1/user/reviews", {
        pageIndex: 0,
        buyerId: this.$store.getters.auth.id
      })
      .then(res => {
        console.log(res);
      });
  },
  data() {
    return {
      customers: "",
      orders: [],
      reviews: [],
      status: ["","대기중", "매칭완료", "제작중", "제작완료", "수령완료"],
      tabValue: 1,
      appBanners: false,
      banners: false,
      notifications: true,
      iconSize: 20,
      activeName: "first",
      edit_status: true,
      detailreviewdialogVisible: false,
      createreviewdialogVisible: false,
    };
  },
  methods: {
    test() {
      // console.log(request);
      // request
      //   .post("/v1/user/portfolios", {
      //     pageIndex: 0
      //   })
      //   .then(res => console.log(res))
      //   .catch(err => console.log(err));
      // requestLogin.defaults.headers.common[
      //   "X-AUTH-TOKEN"
      // ] = localStorage.getItem("AccessToken");
      // requestLogin
      //   .get("/user/member/0")
      //   .then(res => {
      //     console.log(res);
      //   })
      //   .catch(err => console.log(err));
      // axios.defaults.headers.common["Accept"] = "*/*";
      // axios.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      //   "AccessToken"
      // );
      // axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      // axios
      //   .get("http://www.jjcake.co.kr/user/member/11")
      //   .then(res => console.log(res))
      //   .catch(err => console.log(err));
    },
    getStatusColor(status) {
      if (status === 0) {
        return "warning";
      }

      if (status === 1) {
        return "secondary";
      }

      if (status === 2) {
        return "info";
      }

      if (status === 3) {
        return "danger";
      }

      return "success";
    },
    editopen() {
      this.edit_status = false;
    },
    editclose() {
      this.edit_status = true;
    },
    detailreviewdialogclose() {
      this.detailreviewdialogVisible = false;
    },
    createreviewdialogclose() {
      this.createreviewdialogVisible = false;
    },
    createreviewdialogopen(order, event) {
      this.createreviewdialogVisible = true;
      this.selectorder = order;
      console.log('dkssudgktpdyd',order);
    },
    detailreviewdialogopen(review, event) {
      this.detailreviewdialogVisible = true;
      this.selectreview = review;
    },
    remove(review) {
      const idx = this.reviews.findIndex(u => u.id === review.id);
      this.reviews.splice(idx, 1);
    },
    showUser(order) {
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      console.log('tlqkf',order);
      request
        .post("/v1/user/orders", {idorder: order.idorder})
        .then(res => {
          // console.log(res);
          this.$router.push({ name: "detailorder", params: { order: res.data.list[0] } });
        })
        .catch(err => console.log(err));
    },
  },
  name: "mypage_buy",
  components: {
    editbuy,
    detailreview,
    createreview
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

.detailreview .el-dialog {
  min-height: 70%;
}

.detailreview .el-dialog__body {
  padding: -10px 60px 20px 30px;
}

hr {
  border: 0;
  height: 1px;
  background: #cccccc;
}

.tablerate i {
  font-size: 1.5rem;
}
</style>
