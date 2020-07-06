<template>
  <div class="flex xs12 flex-center">
    <div
      v-infinite-scroll="loadMore"
      infinite-scroll-disabled="busy"
      infinite-scroll-distance="1"
      style="max-width: 70rem;"
    >
      <div>
        <div class="markup-tables">
          <va-card>
            <el-tabs v-model="activeName">
              <el-tab-pane label="받은쪽지함" name="first" active>
                <el-dropdown
                  class="messagestate flex flex-right"
                  @command="test1"
                  split-button
                  size="mini"
                  type="warning"
                >
                  {{filterName}}
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="전체">전체쪽지</el-dropdown-item>
                    <el-dropdown-item command="읽음">읽은 쪽지</el-dropdown-item>
                    <el-dropdown-item command="안읽음">읽지 않은 쪽지</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <br />
                <div class="flex flex-center">
                  <table class="va-table va-table--striped va-table--hoverable">
                    <thead class="messagethead">
                      <tr>
                        <th class="state"></th>
                        <th class="sender">보낸사람</th>
                        <th class="title">제목</th>
                        <th class="date">날짜</th>
                        <th class="button"></th>
                      </tr>
                    </thead>

                    <tbody>
                      <tr v-for="message in messages1" :key="message.idmessage">
                        <td>
                          <va-icon name="fa fa-circle" :color="getReadColor(message)" size="8px" />
                        </td>
                        <td>
                          <div v-if="message.marketName">{{message.marketName}}</div>
                          <div v-else>{{message.nickname}}</div>
                        </td>
                        <td @click="detaildialogopen(message, $event)">{{ message.title }}</td>
                        <td>{{ message.sendDate}}</td>
                        <td>
                          <el-button
                            type="warning"
                            icon="el-icon-message"
                            @click="createdialogopen(message, $event)"
                            size="mini"
                            circle
                          ></el-button>
                          <el-button
                            type="danger"
                            icon="el-icon-delete"
                            @click="remove(message)"
                            size="mini"
                            circle
                          ></el-button>
                        </td>
                        <va-list-separator
                          v-if="message.idmessage < messages1.length - 1"
                          :key="'separator' + message.idmessage"
                        />
                      </tr>
                    </tbody>
                  </table>
                </div>
              </el-tab-pane>
              <el-tab-pane label="보낸쪽지함" name="second">
                <el-dropdown
                  class="messagestate"
                  @command="test2"
                  split-button
                  size="mini"
                  type="warning"
                >
                  보기방식
                  <el-dropdown-menu class="flex flex-right" slot="dropdown">
                    <el-dropdown-item command="전체">전체쪽지</el-dropdown-item>
                    <el-dropdown-item command="안읽음">읽지 않은 쪽지</el-dropdown-item>
                    <el-dropdown-item command="읽음">읽은 쪽지</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <br />
                <div class="flex flex-center">
                  <table class="va-table va-table--striped va-table--hoverable">
                    <thead class="messagethead">
                      <tr>
                        <th class="state"></th>
                        <th class="sender">받은 사람</th>
                        <th class="title">제목</th>
                        <th class="date">날짜</th>
                        <th class="button"></th>
                      </tr>
                    </thead>

                    <tbody>
                      <tr v-for="message in messages2" :key="message.idmessage + 'a'">
                        <td>
                          <va-icon name="fa fa-circle" :color="getReadColor(message)" size="8px" />
                        </td>
                        <td>
                          <div v-if="message.marketName">{{message.marketName}}</div>
                          <div v-else>{{message.nickname}}</div>
                        </td>
                        <td @click="detaildialogopen(message, $event)">{{ message.title }}</td>
                        <td>{{ message.sendDate}}</td>
                        <td>
                          <el-button
                            type="warning"
                            icon="el-icon-message"
                            @click="createdialogopen(message, $event)"
                            size="mini"
                            circle
                          ></el-button>
                          <el-button
                            type="danger"
                            icon="el-icon-delete"
                            @click="remove(message)"
                            size="mini"
                            circle
                          ></el-button>
                        </td>
                        <va-list-separator
                          v-if="message.idmessage < messages2.length - 1"
                          :key="'separator' + message.idmessage"
                        />
                      </tr>
                    </tbody>
                  </table>
                </div>
              </el-tab-pane>
            </el-tabs>
          </va-card>
        </div>
      </div>
    </div>
    <el-dialog
      class="detailmessage"
      :visible.sync="detaildialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <detailmessage
        :detaildialogclose="detaildialogclose"
        :createdialogopen="createdialogopen"
        :message="selectmessage"
      ></detailmessage>
    </el-dialog>
    <el-dialog
      class="createmessage"
      :visible.sync="createdialogVisible"
      width="50%"
      :before-close="createhandleClose"
    >
      <createmessage :createdialogclose="createdialogclose" :message="selectmessage"></createmessage>
    </el-dialog>
  </div>
</template>

<script>
import messages from "./message.json";
import { request } from "../../api/api";
import detailmessage from "./detailmessage";
import createmessage from "./createmessage";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    console.log("tqtqtqtqtqtqtqtqtqt");
    console.log(this.$store.getters.auth.id);
    const id = this.$store.getters.auth.id;
    request
      .post("/v1/user/messages", {
        flag: 1,
        pageIndex: 0,
        receiver: id
      })
      .then(res => {
        this.messages1 = res.data.list;
        this.messagesall1 = res.data.list;
        console.log(this.messages1);
        this.busy = false;
        this.nextPage1 = res.data.nextPageIndex;
      })
      .catch(err => console.log(err));
    request
      .post("/v1/user/messages", {
        flag: 2,
        pageIndex: 0,
        sender: this.$store.getters.auth.id
      })
      .then(res => {
        console.log("tq23");
        console.log(res);
        this.messages2 = res.data.list;
        this.messagesall2 = res.data.list;
        console.log(this.messages2);
        this.busy = false;
        this.nextPage2 = res.data.nextPageIndex;
      })
      .catch(err => console.log(err));
  },
  data() {
    return {
      term: null,
      perPage1: "20",
      perPage2: "20",
      messages1: [],
      messages2: [],
      activeName: "first",
      detaildialogVisible: false,
      createdialogVisible: false,
      selectmessage: "",
      filterName: "보기방식",
      busy: true,
      nextPage1: "",
      nextPage2: "",
      nickname: "",
      messagesall1: "",
      messagesall2: ""
    };
  },
  computed: {},
  methods: {
    loadMore() {
      console.log("aabb");
      this.busy = true;
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .post("/v1/user/messages", {
          pageIndex: this.nextPage1,
          receiver: this.$store.getters.auth.id
        })
        .then(res => {
          console.log("dlrpdksehlsk");
          for (let i = 0; i < res.data.list.length; i++) {
            this.messagesall1.push(res.data.list[i]);
          }
          this.nextPage1 = res.data.nextPageIndex;
          if (res.data.list.length === 15) this.busy = false;
        })
        .catch(err => console.log(err));
      request
        .post("/v1/user/messages", {
          pageIndex: this.nextPage2,
          sender: this.$store.getters.auth.id
        })
        .then(res => {
          for (let i = 0; i < res.data.list.length; i++) {
            this.messagesall2.push(res.data.list[i]);
          }
          this.nextPage2 = res.data.nextPageIndex;
          if (res.data.list.length === 15) this.busy = false;
        })
        .catch(err => console.log(err));
    },
    test1(command) {
      // this.is_read = command
      console.log("test1입니다.");
      if (command !== "전체") {
        this.messages1 = this.messageall1;
      }

      if (command === "안읽음") {
        this.messages1 = this.messagesall1.filter(message1 => {
          this.filterName = "읽지 않은 쪽지";
          if (message1.is_read === 0) {
            return message1;
          }
        });
      } else if (command === "읽음") {
        this.messages1 = this.messagesall1.filter(message1 => {
          this.filterName = "읽은 쪽지";
          if (message1.is_read === 1) {
            return message1;
          }
        });
      } else {
        // 전체
        this.filterName = "전체 쪽지";
        this.messages1 = this.messagesall1;
      }
    },
    test2(command) {
      // this.is_read = command

      if (command !== "전체") {
        this.messages2 = this.messagesall2;
      }

      if (command === "안읽음") {
        this.messages2 = this.messagesall2.filter(message2 => {
          this.filterName = "읽지 않은 쪽지";
          if (message2.is_read === 0) {
            return message2;
          }
        });
      } else if (command === "읽음") {
        this.messages2 = this.messagesall2.filter(message2 => {
          this.filterName = "읽은 쪽지";
          if (message2.is_read === 1) {
            return message2;
          }
        });
      } else {
        // 전체
        this.filterName = "전체 쪽지";
        this.messages2 = this.messagesall2;
      }
    },
    reply(message) {
      alert("답장보내기 기능");
    },
    remove(message) {
      const idx = this.messages.findIndex(u => u.id === message.id);
      this.messages.splice(idx, 1);
    },
    getReadColor(message) {
      if (message.is_read === 0) {
        return "danger";
      }

      if (message.is_read === 1) {
        return "grey";
      }

      return "grey";
    },
    detaildialogclose() {
      this.detaildialogVisible = false;
    },
    detaildialogopen(message, event) {
      this.detaildialogVisible = true;
      this.selectmessage = message;
    },
    createdialogclose() {
      this.createdialogVisible = false;
    },
    createdialogopen(message, event) {
      this.createdialogVisible = true;
      this.selectmessage = message;
    },
    getusername(message) {
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .get(`/user/member/${message.sender}`)
        .then(res => {
          this.nickname = res.data.list[0].nickname;
        })
        .catch(err => console.log(err));
    }
  },
  components: {
    detailmessage,
    createmessage
  }
};
</script>

<style lang="scss">
.va-card__header-title {
  font-size: 1rem;
}

.el-dialog__body {
  padding: 30px 60px 20px 30px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}

.detailmessage .el-dialog {
  min-height: 70%;
}

.detailmessage .el-dialog__body {
  padding: -10px 60px 20px 30px;
}

.messagethead {
  .state {
    width: 5%;
  }

  .sender {
    width: 10%;
  }

  .title {
    width: 65%;
  }

  .date {
    width: 10%;
  }

  .button {
    width: 10%;
  }
}

.messagestate {
  position: absolute;
  right: 2.5%;
}
</style>