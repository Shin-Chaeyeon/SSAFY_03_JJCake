const config = state => state.app.config
const palette = state => state.app.config.palette
const isLoading = state => state.app.isLoading
const auth = state => state.auth
const url = state => state.app.URL


export {
  config,
  palette,
  isLoading,
  auth,
  url
}
