<template>
	<!-- 布局容器 -->
	<el-container class="left-container" :style="styleLeftContainer">
		<!-- 左侧 -->
		<el-aside style="background-color: #008000;">
			<el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse"
				background-color="#191a23" text-color="#ffffff" unique-opened="true" router>
			  <el-submenu v-show="item.isShow" class="el-submenu" :index="item.label" v-for="(item,index) in menus" :key="index">
			    <template #title>
			      <i class="el-icon-location"></i>
			      <span>{{item.label}}</span>
			    </template>
				<el-menu-item v-show="subItem.isShow" :index="subItem.path" v-for="(subItem,subIndex) in item.children" :key="subIndex">
					<i class="el-icon-location"></i>
					{{subItem.label}}
				</el-menu-item>
			    <!-- <el-menu-item-group :index="subItem.lable" v-for="(subItem,subIndex) in item.children" :key="subIndex">
			      <template #title>{{subItem.label}}</template>
			    </el-menu-item-group> -->
			  </el-submenu>
			</el-menu>
		</el-aside>
	</el-container>
	
	<el-container class="right-container" :style="styleRightContainer">
		<!-- 顶部 -->
		<el-header>
			<div class="header-options-icon">
				<li><i :class="changeMenuIcon" @click="changeMenuSytle()"></i></li>
				<li><i class="el-icon-refresh" @click="refreshPage()"></i></li>
				<li id="header-options-exit">
					<el-popconfirm
					  confirmButtonText='退出'
					  cancelButtonText='取消'
					  icon="el-icon-info"
					  iconColor="red"
					  title="确定退出系统吗？"
					  @confirm="exitSystem()"
					>
					<template #reference>
					  <el-button type="text">退出</el-button>
					  </template>
					</el-popconfirm>
				</li>
			</div>
		</el-header>
		<el-tabs v-model="activeIndex" type="card" closable 
			@tab-click='tabClick' @tab-remove="tabRemove">
		  <el-tab-pane
		    v-for="(item, index) in openTab"
		    :key="item.name"
		    :label="item.name"
		    :name="item.route"
		  >
		  </el-tab-pane>
		</el-tabs>
		<!-- 主面板 -->
		<el-main>
		    <router-view />
		</el-main>
		
	</el-container>
</template>

<script>

import {menus} from '@/assets/js/menus.js';
export default{
data() {
	let changeMenuIcon = 'el-icon-s-fold';
	let isCollapse = false;
	let leftContainerWidth = '240px';
	let styleLeftContainer = {
		width:leftContainerWidth,
	};
	let styleRightContainer = {
		marginLeft:leftContainerWidth,
	}
	return {
		menus,isCollapse,styleLeftContainer,styleRightContainer,changeMenuIcon
	};
},
computed:{
	activeIndex(){
		// console.log(this.$store.state.activeIndex);
		return this.$store.state.activeIndex;
	},
	openTab(){
		return this.$store.state.openTabs;
		//return this.$store.getters.openTabs;
	}
},
methods: {
	//根据权限控制菜单
	initShowMenuList(){
		// console.log(this.menus);
		// console.log("$store.employee",this.$store.getters.employee);
		//获取自己部门id
		let did = Number(this.$store.getters.employee.departmentId);
		// //获取自己职位id
		let pid = Number(this.$store.getters.employee.positionId);
		// //获取自己职位名称
		let pname = this.$store.getters.employee.position.jobTitle;
		console.log(did,pid,pname);
		if(pname == "超级管理"){
			this.dealSuperAdmin();
			 return;
		}
		
		this.showSelfMenusItem(did,pid,pname);
		this.dealPersonalMenusItem(did,pid,pname);
		this.dealFinance(did,pid,pname);
	},
	//处理财务
	dealFinance(did,pid,pname){
		if(did != 1) return;
		this.menus[7].children[0].isShow = true;
		this.menus[7].children[1].isShow = true;
	},
	//处理超级管理
	dealSuperAdmin(){
		for(let i=2;i<this.menus.length;i++){
			this.menus[i].isShow = true;
			for(let j = 0;j<this.menus[i].children.length;j++){
					this.menus[i].children[j].isShow = true;
			}
		}
	},
	//处理人事部门
	dealPersonalMenusItem(did,pid,pname){
		if(did != 5) return;
		console.log("this.menus[3].children",this.menus[3].children);
		for(let j = 0;j<this.menus[3].children.length;j++){
			if(this.menus[3].children[j].pid == 0) continue;
			//处理管理组
			if(pname == '管理组'){
				this.menus[3].children[j].isShow = true;
			}else{
				//处理其他
				if(pid == this.menus[3].children[j].pid){
					this.menus[3].children[j].isShow = true;
				}
			}
		}
	},
	//处理除了人事部门外，显示可以进入的菜单
	showSelfMenusItem(did,pid,pname){
		for(let i=0;i<this.menus.length;i++){
			if(this.menus[i].did == 0) continue;
			//显示自己的部门
			if(did == menus[i].did){
				// console.log("did == menus[i].did");
				this.menus[i].isShow = true;
				//显示自己的职位
				if(pname == '管理组'){
					for(let j = 0;j<this.menus[i].children.length;j++){
						this.menus[i].children[j].isShow = true;
					}
					break;
				}else{
					for(let j = 0;j<this.menus[i].children.length;j++){
						// console.log("this.menus[i].children",i,j,this.menus[i].children[j]);
						if(pid == this.menus[i].children[j].pid){
							this.menus[i].children[j].isShow = true;
						}
					}
					break;
				}
			}
		}
	},
	//
	//退出系统
	exitSystem(){
		console.log('exitSystem');
		this.$store.commit('clearEmployee');
		this.$store.commit('clearTabs');
		this.$router.push({path: '/login'});
	},
	//刷新页面
	refreshPage(){
		window.location.reload();
	},
	handleOpen(key, keyPath) {
		// console.log(key, keyPath);
	},
	handleClose(key, keyPath) {
		// console.log(key, keyPath);
	},
	changeMenuSytle(){
		if(!this.isCollapse){
			this.isCollapse = !this.isCollapse;
			this.changeMenuIcon = 'el-icon-s-unfold';
			this.styleLeftContainer.width='64px';
			this.styleRightContainer.marginLeft='64px';
		}else{
			this.isCollapse = !this.isCollapse;
			this.styleLeftContainer.width='240px';
			this.styleRightContainer.marginLeft='240px';
			this.changeMenuIcon = 'el-icon-s-fold';
		}
	},
	//tab标签点击时，切换相应的路由
	tabClick(tab){
		this.$store.commit('setActiveIndex',tab.props.name);
		// console.log("tab",tab);
		// console.log("tab-name",tab.props.name);
		// console.log('active',this.activeIndex);
		this.$router.push({path: this.activeIndex});
	},
	//移除tab标签
	tabRemove(targetName){
		//console.log("tabRemove",targetName);
		//首页不删
		if(targetName == '/' || targetName == '/dashboard/workplace'){
			// return;
		}
		this.$store.commit('deleteTabs', targetName);
		if (this.activeIndex === targetName) {
			// 设置当前激活的路由
			//console.log('openTab.length:'+this.openTab.length);
			if (this.openTab && this.openTab.length >= 1) {
				//console.log('=============',this.openTab[this.openTab.length-1].route)
				//console.log(this.openTab[this.openTab.length-1]);
				this.$store.commit('setActiveIndex', this.openTab[this.openTab.length-1].route);
				this.$router.push({path: this.activeIndex});
			} else {
				this.$router.push({path: '/'});
			}
		}
	}
},
watch:{
	'$route'(to,from){
		// console.log("login::",to.path);
		if(to.path == "/login") return;
		//判断路由是否已经打开
		//已经打开的 ，将其置为active
		//未打开的，将其放入队列里
		let flag = false;
		for(let item of this.openTab){
			// console.log("item.name",item.name)
			// console.log("t0.name",to.name)

			if(item.name === to.name){
				// console.log('to.path',to.path);
				this.$store.commit('setActiveIndex',to.path);
				flag = true;
				break;
			}
		}
		// console.log(this.openTab);
		if(!flag){
			// console.log('to.path',to.path);
			this.$store.commit('addTabs', {route: to.path, name: to.name});
			this.$store.commit('setActiveIndex', to.path);
			// console.log('openTab',this.openTab);
		}
		// console.log('ActiveIndex----',this.activeIndex);
	}
},
mounted(){
	// 刷新时以当前路由做为tab加入tabs
	// 当前路由不是首页时，添加首页以及另一页到store里，并设置激活状态
	// 当当前路由是首页时，添加首页到store，并设置激活状态
	if(!this.$store.state.isFristOpen){
		// this.$store.commit('addTabs', {route: '/dashboard/workplace', name: 'workplace'});
		// this.$store.commit('setActiveIndex', '/dashboard/workplace');
		// this.$router.push('/');
		// this.$store.state.isFristOpen = false;
	} 
	// if (this.$route.path !== '/' && this.$route.path !== '/dashboard/workplace') {
	// 	console.log('1');
	// 	this.$store.commit('addTabs', {route: '/dashboard/workplace' , name: 'workplace'});
	// 	this.$store.commit('addTabs', {route: this.$route.path , name: this.$route.name });
	// 	this.$store.commit('setActiveIndex', this.$route.path);
	// } else {
	// 	console.log('2');
	// 	this.$store.commit('addTabs', {route: '/dashboard/workplace', name: 'workplace'});
	// 	this.$store.commit('setActiveIndex', '/dashboard/workplace');
	// 	this.$router.push('/');
	// }
	
	// console.log(this.openTab);
	
	this.initShowMenuList();
}
}
</script>

<style scoped>
@import url("../assets/css/layout/layout.css");
#header-options-exit{
	float: right;
}
</style>
