import axios from 'axios';


const state = {
    token: '',
    id: '',
    role: '',
    nickname: '',
    username: '',
    address: '',
    imageUrl: ''
    // 추가 필요
}

const mutations = {
    setToken(state, token) {
        state.token = token
        localStorage.setItem('AccessToken', token)
    },
    logout(state) {
        if (state.auth.token) {
            state.auth.token = ''
            localStorage.removeItem('AccessToken')
        }
    },
    getInfo(state, user) {
        console.log('qqqqqqq', user)
        state.address = user.address
        state.nickname = user.nickname
        state.id = user.idmember
        state.role = user.admin
        state.imageUrl = user.imageUrl
    }
}

const actions = {
}

export default {
    state,
    mutations,
    actions,
}
