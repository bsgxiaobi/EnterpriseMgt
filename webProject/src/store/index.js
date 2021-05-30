import {
	createStore
} from 'vuex'

export default createStore({
	state: {
		isFristOpen:true,
		openTabs: [], //所有打开的路由
		activeIndex: '/', //激活状态
		employeeId:2100001,//当前员工id
		tempData:Object,//传递的数据
		employee:Object,//用户对象
	},
	getters:{
		activeIndex(state){
			return state.activeIndex;
		},
		openTabs(state){
			return state.openTabs;
		},
		employeeId(state){
			return state.employeeId;
		},
		employee(state){
			return state.employee;
		}
	},
	mutations: {
		//放入用户对象
		addEmployee(state, data){
			this.state.employee = data;
		},
		//清除用户对象
		clearEmployee(state){
			this.state.employee = null;
		},
		// 添加tabs
		addTabs(state, data) {
			this.state.openTabs.push(data);
		},
		// 删除tabs
		deleteTabs(state, route) {
			let index = 0;
			for (let option of state.openTabs) {
				if (option.route === route) {
					break;
				}
				index++;
			}
			this.state.openTabs.splice(index, 1);
		},
		clearTabs(state){
			this.state.openTabs = [];
		},
		// 设置当前激活的tab
		setActiveIndex (state, index) {
			this.state.activeIndex = index;
		},
	},
	actions: {},
	modules: {}
})
