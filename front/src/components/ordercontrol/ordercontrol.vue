<template>
  <div class="flex xs12 flex-center">
    <div class="ordercontrolcard">
      <div style="text-align: center; font-size: 50px;">
        <b>주문현황관리</b>
      </div>
      <br />
      <va-card>
        <div class="row">
          <div class="flex xs6 sm3 lg3 xl3">
            <div class="typography content">
              <p class="ordercontrollist display-4">
                대기중
                <el-badge :value="list1.length" class="item"></el-badge>
              </p>
            </div>
            <div class="list-group-item" v-for="(element, index) in list1" :key="element.idorder">
              <el-card class="box-card1" style="max-width: 95%;">
                <div slot="header" class="clearfix display-5">
                  <span>{{ element.title }}</span>
                  <el-button
                    style="float: right;"
                    type="warning"
                    icon="el-icon-message"
                    size="mini"
                    circle
                  ></el-button>
                </div>
                <div class="el-row">
                  <el-button
                    type="warning"
                    plain
                    disabled
                    circle
                  >{{receive_status[element.receive_way]}}</el-button>
                  <p class="display-4" style="float: right;">D-{{countDDay(element.receiveDate)}}</p>
                </div>
              </el-card>
              <br />
            </div>
          </div>

          <div class="flex xs6 sm3 lg3 xl3">
            <div class="typography content">
              <p class="display-4">
                매칭완료
                <el-badge :value="list2.length" class="item"></el-badge>
              </p>
            </div>
            <draggable class="list-group" :list="list2" group="people" @change="log">
              <div class="list-group-item" v-for="(element, index) in list2" :key="element.name">
                <el-card class="box-card2" style="max-width: 95%;">
                  <div slot="header" class="clearfix display-5">
                    <span>{{ element.title }}</span>
                    <el-button
                      style="float: right;"
                      type="warning"
                      icon="el-icon-message"
                      size="mini"
                      circle
                    ></el-button>
                  </div>
                  <div class="el-row">
                    <el-button
                      type="warning"
                      plain
                      disabled
                      circle
                    >{{receive_status[element.receive_way]}}</el-button>
                    <p class="display-4" style="float: right;">D-{{countDDay(element.receive_date)}}</p>
                  </div>
                </el-card>
                <br />
              </div>
            </draggable>
          </div>
          <div class="flex xs6 sm3 lg3 xl3">
            <div class="typography content">
              <p class="display-4">
                제작중
                <el-badge :value="list3.length" class="item"></el-badge>
              </p>
            </div>
            <draggable class="list-group" :list="list3" group="people" @change="log">
              <div class="list-group-item" v-for="(element, index) in list3" :key="element.name">
                <el-card class="box-card3" style="max-width: 95%;">
                  <div slot="header" class="clearfix display-5">
                    <span>{{ element.title }}</span>
                    <el-button
                      style="float: right;"
                      type="warning"
                      icon="el-icon-message"
                      size="mini"
                      circle
                    ></el-button>
                  </div>
                  <div class="el-row">
                    <el-button
                      type="warning"
                      plain
                      disabled
                      circle
                    >{{receive_status[element.receive_way]}}</el-button>
                    <p class="display-4" style="float: right;">D-{{countDDay(element.receive_date)}}</p>
                  </div>
                </el-card>
                <br />
              </div>
            </draggable>
          </div>
          <div class="flex xs6 sm3 lg3 xl3">
            <div class="typography content">
              <p class="display-4">
                제작완료
                <el-badge :value="list4.length" class="item"></el-badge>
              </p>
            </div>
            <draggable class="list-group" :list="list4" group="people" @change="log">
              <div class="list-group-item" v-for="(element, index) in list4" :key="element.name">
                <el-card class="box-card4" style="max-width: 95%;">
                  <div slot="header" class="clearfix display-5">
                    <span>{{ element.title }}</span>
                    <el-button
                      style="float: right;"
                      type="warning"
                      icon="el-icon-message"
                      size="mini"
                      circle
                    ></el-button>
                  </div>
                  <div class="el-row">
                    <el-button
                      type="warning"
                      plain
                      disabled
                      circle
                    >{{receive_status[element.receive_way]}}</el-button>
                    <p class="display-4" style="float: right;">D-{{countDDay(element.receive_date)}}</p>
                  </div>
                </el-card>
                <br />
              </div>
            </draggable>
          </div>
        </div>
      </va-card>
    </div>
  </div>
</template>
<script>
import draggable from "vuedraggable";
import { request } from "../../api/api";

export default {
  name: "ordercontrol",
  display: "ordercontrol",
  order: 1,
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    const data = {};
    request
      .post("/v1/user/orders", data)
      .then(res => {
        for (let i = 0; i < res.data.list.length; i++) {
          if (res.data.list[i].status === 1) {
            this.list1.push(res.data.list[i]);
          } else if (res.data.list[i].status === 2) {
            this.list2.push(res.data.list[i]);
          } else if (res.data.list[i].status === 3) {
            this.list3.push(res.data.list[i]);
          } else if (res.data.list[i].status === 4) {
            this.list4.push(res.data.list[i]);
          }
        }
        console.log(res);
      })
      .catch(err => console.log(err));
  },
  components: {
    draggable
  },
  watch: {
    list2: function() {
      if (this.list2_length < this.list2.length) {
        console.log("list2 추가되었어요!");
        this.list2_length = this.list2.length;
      } else {
        this.list2_length = this.list2.length;
      }
    },
    list3: function() {
      if (this.list3_length < this.list3.length) {
        console.log("list3 추가되었어요!");
        this.list3_length = this.list3.length;
      } else {
        this.list3_length = this.list3.length;
      }
    },
    list4: function() {
      if (this.list4_length < this.list4.length) {
        console.log("list4 추가되었어요!");
        this.list4_length = this.list4.length;
      } else {
        this.list4_length = this.list4.length;
      }
    }
  },
  data() {
    return {
      receive_status: ["🎁", "🚚"],
      list1: [],
      list2: [],
      list3: [],
      list4: [],
      list2_length: 0,
      list3_length: 0,
      list4_length: 0
    };
  },
  methods: {
    add: function() {
      this.list.push({ name: "Juan" });
    },
    replace: function() {
      this.list = [{ name: "Edgard" }];
    },
    clone: function(el) {
      return {
        name: el.name + " cloned"
      };
    },
    log: function(evt) {
      window.console.log(evt);
    },
    getReceiveIcon(way) {
      if (way === 0) {
        console.log(way);
        return "el-icon-shopping-bag-1";
      }

      if (way === 1) {
        console.log(way);
        return "el-icon-box";
      }

      return "el-icon-shopping-bag-1";
    },
    countDDay(date) {
      console.log(date);
      var day = Math.ceil((new Date(date) - new Date()) / (1000 * 3600 * 24));
      return day;
    }
  }
};
</script>

<style lang="scss">
.ordercontrolcard {
  max-width: 70rem;

  .display-4 {
    text-align: center;
  }
}

.box-card1 .el-card__header {
  background-color: rgba(246, 212, 158, 0.2);
}

.box-card2 .el-card__header {
  background-color: rgba(249, 167, 39, 0.25);
}

.box-card3 .el-card__header {
  background-color: rgba(233, 158, 96, 0.3);
}

.box-card4 .el-card__header {
  background-color: rgba(206, 181, 159, 0.3);
}
</style>