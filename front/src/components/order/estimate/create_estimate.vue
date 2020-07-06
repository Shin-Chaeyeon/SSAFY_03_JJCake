<template>
  <div class="creates">
    <va-item>
      <va-item-section avatar>
        <div class="avatar">
          <img :src="data.imageUrl" :alt="data.name" />
        </div>
      </va-item-section>
      <div>
        <va-item-section>
          <va-item-label class="display-3">{{ data.nickname }}</va-item-label>
          <hr />
          <va-item-label>
            {{ data.address }}
            {{ data.email}}
          </va-item-label>
        </va-item-section>
      </div>
      <div class="estimate-price">
        <el-input placeholder="가격을 입력하세요" prefix-icon="el-icon-money" v-model="price">
          <template slot="append">원</template>
        </el-input>
      </div>
    </va-item>
    <div class="estimate-content row">
      <div class="left flex xs10 md10">
        <el-input type="textarea" cols="1" rows="3" v-model="contents" placeholder="내용을 입력해주세요"></el-input>
      </div>
      <div class="flex xs1 md1"></div>
      <div class="flex xs1 md1">
        <el-button type="warning" @click="submit">작성</el-button>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import { request } from '../../../api/api'
export default {
  name: 'createestimate',
  props: {
    makeestimate: Function,
    order: Object,
  },
  created () {
    this.data = this.$store.getters.auth
  },

  data () {
    return {
      contents: '',
      price: 0,
    }
  },
  components: {},
  methods: {
    submit () {
      const value = {
        contents: this.contents,
        price: this.price,
      }

      this.makeestimate(value)
      request.defaults.headers.common['X-AUTH-TOKEN'] = localStorage.getItem(
        'AccessToken',
      )
      console.log('오더입니다', this.order)
      const seller = this.$store.getters.auth.id
      console.log('셀러입니다', seller)
      request
        .post('/v1/user/estimate', {
          contents: this.contents,
          orderId: this.order.idorder,
          price: this.price,
          sellerId: seller,
        })
        .then(res => {
          console.log('kdm', res)
        })
        .catch(err => console.log('에러났어요', err))
    },
  },
}
</script>

<style lang="scss">
.creates {
  background-color: rgba(0, 0, 0, 0.1);
}
</style>
