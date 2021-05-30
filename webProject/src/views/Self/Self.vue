<template>
	<div id="attendance-main">
		<el-tabs v-model="activeName" @tab-click="handleClick">
		    
			<el-tab-pane label="个人信息" name="first">
		    	<Info></Info>
		    </el-tab-pane>
			<el-tab-pane label="账号管理" name="second">
		    	<Password></Password>
		    </el-tab-pane>
		</el-tabs>
		
	</div>
</template>

<script>

import Info from '@/components/Self/Info.vue';
import Password from '@/components/Self/Password.vue';
export default{
	name:'Selft',
	components:{
		Info,Password
	},
	data(){
		return{
			activeName:'first',
		}
	}
}
</script>

<style scoped>
@import url("../../assets/css/Personal/Attendance.scss");
</style>
