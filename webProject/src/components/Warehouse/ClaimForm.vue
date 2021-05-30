<template>
	<div id="apply-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			
			<el-form-item label="原因:">
			  <el-input style="width: 600px;" v-model="formData.cause" placeholder="原因"></el-input>
			</el-form-item>
			<div id="item-input-table">
			<el-table
				:data="tableData"
				stripe
				border
				max-height="300px">
				<el-table-column type="index" :index="indexMethod" width="30"></el-table-column>
				<el-table-column label="物品编码" width="80">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.p_id"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="物品名称" width="100">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.name"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="规格型号" width="80">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.model"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="单位" width="50">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.pic"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="price" label="单价" width="75"></el-table-column>
				<el-table-column label="数量" width="75">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.num"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="total" label="金额" width="75">
					<template #default="scope">
						<span v-show="isAdd">{{claimItemTotal(scope.$index,scope.row)}}</span>
						<span v-show="!isAdd">{{scope.row.total}}</span>
					</template>
				</el-table-column>
				<el-table-column prop="surplusNum" label="账面数量" width="75"></el-table-column>
				<el-table-column label="操作">
					<template #default="scope">
						<el-button :disabled="!isAdd" size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<el-form-item v-show="!isApply" label="仓库:">
				  <el-select v-model="formData.warehouse" class="el-select-sex" placeholder="未指定">
				    <el-option label="仓库1" value="仓库1"></el-option>
				    <el-option label="仓库2" value="仓库2"></el-option>
				  </el-select>
				</el-form-item>
				<el-form-item v-show="isExamine" label="审核状态:" style="padding-left: 30px;">
				<el-switch
				  v-model="currentApplyState"
				  active-color="#13ce66"
				  inactive-color="#ff4949"
				  active-text="通过"
				  inactive-text="拒绝"></el-switch>
				</el-form-item>
				<el-button v-show="isAdd" type="primary" @click="addTableItem()" size="medium" icon="el-icon-plus">增加</el-button>
				<div class="footer-item-left">
					金额合计：
					<span v-show="isAdd">{{claimTotalSum}}</span>
					<span v-show="!isAdd">{{formData.claimTotal}}</span>
					元
				</div>
			</div>
		
		</el-form>
	</div>
</template>

<script>
let formData = {
	cause:'',
	warehouse:'',
	claimTotal:0
};
let tableData = [{
	p_id:'',
	name:'',
	model:'',
	pic:'',
	price:10,
	num:0,
	total:0,
	surplus_num:0,
}
];
let currentClaimTotal = -1;
let currentApplyState = false;
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'ClaimForm',
	computed:{
		inputEvent(row){
			console.log(row);
		},
		claimItemTotal(){
			return function(index,row){
				this.tableData[index].total = row.price * row.num;
				return this.tableData[index].total;
			}
		},
		claimTotalSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].price * this.tableData[i].num;
				}
				this.currentClaimTotal = sum;
				return sum;
			}
		}
	},
	props:{
		transmitId:String,isAdd:Boolean,isExamine:Boolean,isApply:Boolean
	},
	data(){
		return{
			formData,tableData,currentClaimTotal,currentApplyState
		}
	},
	methods:{
		handleDelete(index, row) {
			console.log(index, row);
			this.tableData.splice(index,1);
		},
		indexMethod(index){
			return ++index;
		},
		addTableItem(){
			let tableDataItem = {
				p_id:'',name:'',model:'',pic:'',price:10,num:0,total:0,surplus_num:0};
			this.tableData.push(tableDataItem);
		},
		queryFun(claimId){
			this.queryItemInfo(claimId);
			this.queryInfo(claimId);
		},
		//重置信息
		resetInfo(){
			let tableDataTemp = [{
				p_id:'',name:'',model:'',pic:'',price:10,num:0,total:0,surplus_num:0,}
			];
			this.tableData = tableDataTemp;
			let formDataTemp = {cause:'',warehouse:'',claimTotal:0};
			this.formData = formDataTemp;
			this.currentClaimTotal = -1;
		},
		//拉取清单
		async queryItemInfo(claimId){
			let params = {
				claimId:claimId
			};
			GetForParams('/claim-item/item',params).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log("失败");
			});
		},
		//拉取信息
		async queryInfo(claimId){
			let params = {
				claimId:claimId
			};
			GetForParams('/inventory-claim/one',params).then(res=>{
				this.formData = res.data.data;
			}).catch(err=>{
				console.log("失败");
			});
		}
	},
	mounted() {
		// console.log('transmitId::::'+this.transmitId);
		if(this.isAdd) return;
		this.queryItemInfo(this.transmitId);
		this.queryInfo(this.transmitId);
	}
}
</script>

<style lang="scss">
/* #item-input-table .el-table td{
	padding: 7px 0px;
}*/
#item-input-table .el-table td .cell{
	padding: 0 0;
	text-align: center;
}
#item-input-table th .cell{
	text-align: center;
	padding: 0px 0px;
}
#item-input-table .el-table__row td{
	padding: 5px 0px;
}
.table-el-input{
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
}
.table-el-input .el-input__inner{
	padding: 0px 0px;
	border:none;
	height: 30px;
	line-height: 30px;
	text-align: center;
	vertical-align: middle;
	font-size: 14px;
}
.footer-item{
	margin-top: 5px;
}
.footer-item-left{
	float: right;
	width: 200px;
	line-height: 36px;
	height: 36px;
	font-size: 16px;
	text-align: right;
}
</style>
