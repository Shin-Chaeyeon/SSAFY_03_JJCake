import store from '../store/index'

export default function setInterceptors(instance) {
    instance.interceptors.request.use(
        function (config) {
            instance.defaults.headers.common['X-AUTH-TOKEN'] = localStorage.getItem('AccessToken')
            return config;
        },
        function (error) {
            return Promise.reject(error);
        },
    );

    instance.interceptors.response.use(
        function (response) {
            // 응답 데이터를 가공
            // ...
            return response;
        },
        function (error) {
            // 오류 응답을 처리
            // ...
            return Promise.reject(error);
        });
}