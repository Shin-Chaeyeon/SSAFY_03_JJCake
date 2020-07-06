<template>
  <nav class="app-navbar" :style="navbarStyle">
    <div class="app-navbar__content row" @click="aa">
      <div class="app-navbar__container">
        <router-link class="app-navbar__logo" :to="{name: 'main', params: {yes: '123123'}}">
          <img style="max-height: 50px; max-width: 100px;" src="./logo.png" alt />
        </router-link>
      </div>
    </div>
    <!-- <app-navbar-actions
        class="app-navbar__actions md5 lg4"
        :user-name="userName"
        :is-top-bar.sync="isTopBarProxy"
    />-->
    <div class="app-navbar__actions md5 lg4">
      <router-link to="/admin/order">
        <va-button flat color="dark" small>{{ $t('주문리스트') }}</va-button>
      </router-link>
      <router-link to="/admin/portfolio">
        <va-button flat color="dark" small>{{ $t('모아보기') }}</va-button>
      </router-link>
      <router-link v-if="authrole===2" to="/admin/ordercontrol">
        <va-button flat color="dark" small>{{ $t('주문현황관리') }}</va-button>
      </router-link>

      <router-link v-if="authrole===2" :to="{ name: 'mypage_sell', params: {id: id}}">
        <va-button flat color="dark" small>{{ $t('판매자마이페이지') }}</va-button>
      </router-link>
      <router-link v-if="authrole===1" to="/admin/mypage_buy">
        <va-button flat color="dark" small>{{ $t('고객마이페이지') }}</va-button>
      </router-link>
      <router-link to="/admin/message">
        <va-button flat color="dark" small>{{ $t('쪽지함') }}</va-button>
      </router-link>
      <router-link to="/auth">
        <va-button flat color="dark" small>{{ $t('로그아웃') }}</va-button>
      </router-link>
    </div>
  </nav>
</template>

<script>
import VaIconVuestic from "../../../iconset/VaIconVuestic";
import VaIconMenu from "../../../iconset/VaIconMenu";
import VaIconMenuCollapsed from "../../../iconset/VaIconMenuCollapsed";
import AppNavbarActions from "./components/AppNavbarActions";
import { colorShiftHsl, ColorThemeMixin } from "../../../services/vuestic-ui";
import { request } from "../../../api/api";

export default {
  name: "app-navbar",
  mixins: [ColorThemeMixin],
  inject: ["contextConfig"],
  components: {
    VaIconVuestic,
    VaIconMenu,
    VaIconMenuCollapsed,
    AppNavbarActions
  },
  props: {
    isTopBar: {
      type: Boolean,
      required: true
    },
    minimized: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      userName: "Vasili S",
      authrole: "",
      id: ""
    };
  },
  mounted() {
    if (localStorage.getItem("AccessToken")) {
      request.defaults.headers.common["X-AUTH-TOKEN"] = localStorage.getItem(
        "AccessToken"
      );
      request
        .get("/user/member/0")
        .then(res => {
          this.$store.commit("getInfo", res.data.list[0]);
          this.authrole = res.data.list[0].admin;
          this.id = res.data.list[0].idmember;
          console.log("aa", res);
        })
        .catch(err => console.log(err));
    }
  },
  methods: {
    aa() {
      console.log(this.authrole);
    }
  },
  computed: {
    isTopBarProxy: {
      get() {
        return this.isTopBar;
      },
      set(isTopBar) {
        this.$emit("update:isTopBar", isTopBar);
      }
    },
    minimizedProxy: {
      get() {
        return this.minimized;
      },
      set(minimized) {
        this.$emit("update:minimized", minimized);
      }
    },
    navbarStyle() {
      const style = {
        backgroundColor: "white",
        padding: "10px 10%"
      };
      if (this.contextConfig.gradient) {
        style.backgroundColor = colorShiftHsl(this.$themes.secondary, {
          s: -13,
          l: 15
        }).css;
      }

      if (this.contextConfig.shadow === "sm") {
        style.boxShadow = !this.isTopBar
          ? "0 2px 3px 0 rgba(52, 56, 85, 0.25)"
          : null;
      }
      return style;
    },

    shapeStyle() {
      return {
        borderTopColor: this.contextConfig.gradient
          ? colorShiftHsl(this.$themes.secondary, {
              h: -1,
              s: -11,
              l: 10
            }).css
          : "transparent"
      };
    }
  }
};
</script>

<style lang="scss">
$nav-border-side-width: 3.1875rem;

.app-navbar {
  transition: background-color 0.3s ease; /* sidebar's bg color transitions as well -> consistency */
  display: flex;
  padding: 1rem 1rem;
  z-index: 1;
  background-color: #ffffff1f !important;

  &__content {
    display: flex;
    z-index: 1;
    align-items: center;
    flex-direction: row;
    flex-wrap: wrap;
    height: 100%;
    flex: 1 1 auto;
    max-width: 80%;
  }

  &__center {
    display: flex;
    margin-left: 3rem;
    justify-content: space-between;
    align-items: center;
  }

  &__text {
    color: $lighter-gray;
  }

  &__button {
    width: 10rem;
    margin: 0 0 0 1rem !important;
    font-weight: bold;

    .va-button__content__icon-left.fa-github {
      font-size: 1.5rem;
    }
  }

  &__menu {
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1rem;
    margin-right: 1.5rem;
  }

  &__menu-container {
    display: flex;
    flex-wrap: nowrap;
    height: 1.5rem;
  }

  &__logo {
    width: 9.5rem;
    height: auto;
    align-items: center;

    & * {
      max-height: 100%;
      max-width: 100%;
      width: 100%;
      height: 100%;
      display: block;
    }
  }

  &__actions {
    justify-content: flex-end;
  }

  &__mailto-link:hover {
    filter: brightness(85%);
  }

  &__shape {
    transition: border-top-color 0.3s ease; /* sidebar's bg color transitions as well -> consistency */
    width: 33%;
    max-width: 467px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    margin: auto;
    border-top: 4.215rem solid transparent; // hardcoded size
    border-left: $nav-border-side-width solid transparent;
    border-right: $nav-border-side-width solid transparent;
    height: 0;
  }

  @include media-breakpoint-down(lg) {
    &__button {
      display: none !important;
    }
  }

  @include media-breakpoint-down(md) {
    &__center {
      display: none !important;
    }
  }

  @include media-breakpoint-down(sm) {
    &__content {
      align-items: flex-end;
    }

    &__actions {
      margin-top: 1.25rem;
      justify-content: space-between;
      width: 100%;
    }

    &__shape {
      display: none;
    }
  }
}
</style>
