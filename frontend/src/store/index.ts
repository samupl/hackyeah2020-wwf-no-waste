import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authToken: "",
  },
  mutations: {
    setToken(state, token: string) {
      state.authToken = token;
    }
  },
  actions: {},
  modules: {},
  getters: {
    isLoggedIn(state) {
      return state.authToken !== ""
    }
  }
});
