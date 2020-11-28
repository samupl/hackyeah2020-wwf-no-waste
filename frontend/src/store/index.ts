import Vue from "vue";
import Vuex from "vuex";
import VuexPersistence from "vuex-persist";
const vuexLocal = new VuexPersistence({
  storage: window.localStorage
});

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authToken: ""
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
      return state.authToken !== "";
    }
  },
  plugins: [vuexLocal.plugin]
});
