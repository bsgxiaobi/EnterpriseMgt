<template>
  <!-- <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div> -->
  <router-view/>
</template>
<script>
export default {
	created() {
		// 在页面加载时读取sessionStorage里的状态信息
		if (sessionStorage.getItem('store')) {
		  // 存储状态
		  // this.$store.commit('clearTabs');
		  // console.log('replaceState之前',this.$store.state.openTabs);
		  this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))))
		  // console.log('replaceState之后',this.$store.state.openTabs);
		}
		// 在页面刷新时将vuex里的信息保存到sessionStorage里
		window.addEventListener('beforeunload', () => {
			// sessionStorage.setItem('store',null);
			// session存储
			sessionStorage.setItem('store', JSON.stringify(this.$store.state));
		})
	}
}
</script>
<style>

</style>
