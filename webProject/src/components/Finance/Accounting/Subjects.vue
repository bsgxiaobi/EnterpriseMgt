<template>
	<!-- 会计科目 -->
	<div>
		<div id="subjects-header">
			<li :class="{'header-active':currentSelect == '资产类'}" @click="switchEvent('资产类')">资产类</li>
			<li :class="{'header-active':currentSelect == '负债类'}" @click="switchEvent('负债类')">负债类</li>
			<li :class="{'header-active':currentSelect == '共同类'}" @click="switchEvent('共同类')">共同类</li>
			<li :class="{'header-active':currentSelect == '成本类'}" @click="switchEvent('成本类')">成本类</li>
			<li :class="{'header-active':currentSelect == '损益类'}" @click="switchEvent('损益类')">损益类</li>
			<li :class="{'header-active':currentSelect == '所有者权益类'}" @click="switchEvent('所有者权益类')">所有者权益类</li>
		</div>
		<el-table
			:data="currentItem"
			@row-dblclick="entrySelectItem"
			style="width: 100%;margin-bottom: 10px;"
			height="300"
			stripe
			border
			highlight-current-row
			title="双击选定"
			:cell-style="subjectsTableRowClass">
			<el-table-column prop="asId" label="会计科目" width="100"></el-table-column>
			<el-table-column prop="name" label="科目名称" width="170"></el-table-column>
			<el-table-column prop="range" label="适用范围"></el-table-column>
		</el-table>
	</div>
</template>

<script>
let currentItem = [];
let currentSonOperationRow = null;
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Subjects',
	data(){
		return{
			activeName: 'first',
			currentSelect:'资产类',
			subjectsItem:[],
			currentItem,
			currentSonOperationRow
		}
	},
	methods:{
		subjectsTableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				cursor: 'pointer'
			}
		},
		entrySelectItem(row){
			this.currentSonOperationRow = row;
			//console.log(this.currentSonOperationRow);
			//向上找到所在的所在的实例位置
			this.$parent.$parent.$parent.$parent.enrtySonSelectedEvent();
		},
		switchEvent(type){
			this.currentSelect = type;
			this.currentItem = [];
			//console.log()
			for(let i=0;i<this.subjectsItem.length;i++){
				if(this.subjectsItem[i].type == type){
					this.currentItem.push(this.subjectsItem[i]);
				}
			}
			// this.$refs.subjectTable.bodyWrapper.scrollTop = 0;
		},
		//页面打开时拉取所有信息
		getInitInfo(){
			getDataForNullParams("/accounting-subjects/all").then(res=>{
				this.subjectsItem = res.data.data;
				this.switchEvent('资产类');
				// console.log(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.getInitInfo();
	}
}
</script>

<style lang="scss" scoped>
#subjects-header{
	height: 39px;
	border-bottom: 2px #cccccc solid;
	margin-bottom: 20px;
	margin-top: 15px;
}
#subjects-header li{
	list-style: none;
	float: left;
	cursor: pointer;
	display: block;
	padding: 10px 0px;
	margin-right: 20px;
}
.header-active{
	color: #409EFF;
	border-bottom: 2px #409EFF solid;
}
</style>
