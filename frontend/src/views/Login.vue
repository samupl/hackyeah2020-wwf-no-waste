<template>
  <v-container fluid>
    <v-row align="center" justify="center">
      <v-col align="center">
        <v-card :loading="loading" max-width="500px">
          <v-card-title class="justify-center">Product Panda</v-card-title>
          <v-card-subtitle class="justify-center">Sign in</v-card-subtitle>

          <v-card-text>
            <v-alert
              class="text-left"
              v-model="hasError"
              type="error"
              transition="slide-y-transition"
            >
              {{ error }}
            </v-alert>
            <v-text-field
              label="Login"
              prepend-icon="mdi-account-circle"
              v-model="login"
              :disabled="loading"
            ></v-text-field>
            <v-text-field
              label="Password"
              prepend-icon="mdi-lock"
              type="password"
              v-model="password"
              :disabled="loading"
            ></v-text-field>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="success" @click="submit" :loading="loading">
              Sign in
            </v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

const successResponse = {
  data: {
    token: "123"
  }
};
const errorResponse = {
  response: {
    data: {
      reason: "Invalid login or password"
    }
  }
};

@Component
export default class Login extends Vue {
  public loading = false;
  public error = "";

  public login = "";
  public password = "";

  public validLogin = "admin";
  public validPassword = "123";

  public async submit() {
    this.loading = true;

    // TODO: remove once auth endpoint is ready
    let promise = new Promise((resolve, reject) => {
      setTimeout(() => {
        reject(errorResponse);
      }, 1000);
    });
    this.error = "";
    if (this.login == this.validLogin && this.password == this.validPassword)
      promise = new Promise(resolve => {
        setTimeout(() => {
          resolve(successResponse);
        }, 1000);
      });

    try {
      const response = await promise;
      this.$store.commit("setToken", response.data.token);
      await this.$router.push("/products");
    } catch (err) {
      this.handleError(err);
    } finally {
      this.loading = false;
    }
  }

  public handleError(err) {
    this.error = err.response.data.reason;
  }

  public get hasError() {
    return this.error.length > 0;
  }
}
</script>
