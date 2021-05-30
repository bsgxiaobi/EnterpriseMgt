<template>
	<div id="workplace-page">
		<span>欢迎你，{{employeeName}}</span>
	</div>
</template>

<script>

import {getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Workplace',
	data(){
		return{
			employeeName:''
		}
	},
	computed:{
	},
	methods:{
		
	},
	mounted() {
		this.employeeName = this.$store.getters.employee.name;
	}
}
</script>

<style scoped>
#workplace-page{
	font-size: 24px;
}
</style>
