<template>
  <div class="flex flex-center">
    <div style="max-width: 70rem;">
      <va-card :title="$t('주문서리스트')">
        <div class="row align--center">
          <div class="flex xs12 md6">
            <va-input :value="term" :placeholder="$t('검색하세요')" @input="search" removable>
              <va-icon name="fa fa-search" slot="prepend" />
            </va-input>
          </div>

          <div class="flex xs12 md3 offset--md3">
            <va-select
              v-model="perPage"
              :label="$t('tables.perPage')"
              :options="perPageOptions"
              noClear
            />
          </div>
        </div>

        <el-dropdown class="dwst" style="left: 90%;" @command="test" split-button type="warning">
          {{filterName}}
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="전체">전체</el-dropdown-item>
            <el-dropdown-item command="대기중">대기중</el-dropdown-item>
            <el-dropdown-item command="매칭완료">매칭완료</el-dropdown-item>
            <el-dropdown-item command="제작중">제작중</el-dropdown-item>
            <el-dropdown-item command="제작완료">제작완료</el-dropdown-item>
            <el-dropdown-item command="수령완료">수령완료</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <va-data-table
          :fields="fields"
          :data="filteredData"
          :per-page="parseInt(perPage)"
          @row-clicked="showUser"
          clickable
        >
          <template slot="trend" slot-scope="props">
            <va-icon :name="getTrendIcon(props.rowData)" :color="getTrendColor(props.rowData)" />
          </template>

          <template slot="status" slot-scope="props">
            <va-badge
              :color="getStatusColor(props.rowData.status)"
            >{{ state[props.rowData.status] }}</va-badge>
          </template>

          <template slot="actions" slot-scope="props">
            <va-button
              v-if="props.rowData.hasReport"
              small
              color="danger"
              class="ma-0"
            >{{ $t('tables.report') }}</va-button>
          </template>
        </va-data-table>
        <br />
        <el-button type="warning" style="float: right;" @click="makeorder">글쓰기</el-button>
      </va-card>
    </div>
  </div>
</template>

<script>
import { debounce } from "lodash";
import { request } from "../../api/api";

export default {
  created() {
    request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
      "AccessToken"
    );
    request
      .post("/v1/user/orders", {})
      .then(res => {
        console.log(res);
        this.users = res.data.list;
        this.usersall = res.data.list;
      })
      .catch(err => console.log(err));
  },
  data() {
    return {
      term: null,
      perPage: "10",
      perPageOptions: ["4", "8", "10", "20"],
      users: [],
      state: ["", "대기중", "매칭완료", "제작중", "제작완료", "수령완료"],
      filterName: "전체"
    };
  },
  computed: {
    fields() {
      return [
        {
          name: "title",
          title: this.$t("제목"),
          width: "40%"
        },
        {
          name: "__slot:status",
          title: this.$t("주문상태"),
          width: "10%"
        },
        {
          name: "maxPrice",
          title: this.$t("가격"),
          width: "10%"
        },
        {
          name: "createDate",
          title: this.$t("작성날짜"),
          // dataClass: 'text-right',
          width: "20%"
        },
        {
          name: "receiveDate",
          title: this.$t("수령날짜"),
          width: "10%"
        }
      ];
    },
    filteredData() {
      if (!this.term || this.term.length < 1) {
        return this.users;
      }

      return this.users.filter(item => {
        return item.title.toLowerCase().includes(this.term.toLowerCase());
      });
    }
  },
  methods: {
    getStatusColor(status) {
      if (status === 1) {
        return "warning";
      }

      if (status === 2) {
        return "secondary";
      }

      if (status === 3) {
        return "info";
      }

      if (status === 4) {
        return "danger";
      }

      return "success";
    },

    test(command) {
      this.filterName = command;

      if (command !== "전체") {
        this.users = this.usersall;
      }

      if (command === "대기중") {
        this.users = this.users.filter(user => {
          if (user.status === 1) {
            return user;
          }
        });
      } else if (command === "매칭완료") {
        this.users = this.users.filter(user => {
          if (user.status === 2) {
            return user;
          }
        });
      } else if (command === "제작중") {
        this.users = this.users.filter(user => {
          if (user.status === 3) {
            return user;
          }
        });
      } else if (command === "제작완료") {
        this.users = this.users.filter(user => {
          if (user.status === 4) {
            return user;
          }
        });
      } else if (command === "수령완료") {
        this.users = this.users.filter(user => {
          if (user.status === 5) {
            return user;
          }
        });
      } else {
        // 전체
        this.users = this.usersall;
      }
    },
    getTrendIcon(user) {
      if (user.trend === "up") {
        return "fa fa-caret-up";
      }

      if (user.trend === "down") {
        return "fa fa-caret-down";
      }

      return "fa fa-minus";
    },
    getTrendColor(user) {
      if (user.trend === "up") {
        return "primary";
      }

      if (user.trend === "down") {
        return "danger";
      }

      return "grey";
    },
    showUser(orderinfo) {
      this.$router.push({ name: "detailorder", params: { order: orderinfo } });
    },

    search: debounce(function(term) {
      this.term = term;
    }, 400),
    makeorder() {
      this.$router.push({ path: "/admin/makeorder" });
    }
  }
};
</script>

<style lang="scss">
.va-table th {
  font-size: 1rem;
  letter-spacing: 0.6px;
  line-height: 1.2;
  font-weight: bold;
  text-transform: uppercase;
  color: #34495e;
  border-bottom: 2px solid #34495e;
}

.va-card__header-title {
  font-size: 1rem;
  color: rgb(230, 162, 60) !important;
}

.va-select__label {
  color: rgb(230, 162, 60) !important;
}

.va-data-table__pagination {
  color: rgb(230, 162, 60) !important;

  .va-button {
    color: rgb(230, 162, 60) !important;
    border-color: rgb(230, 162, 60) !important;
  }

  .va-button--active {
    background: rgb(255, 227, 186) !important;
  }
}

.va-card__body {
  overflow-x: hidden;
}
</style>
