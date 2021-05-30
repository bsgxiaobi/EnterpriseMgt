<template>
	<div class="office-disk-main">
		<SalesSearchForm></SalesSearchForm>
		<!-- <div>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="deliveryDialogVisible = true" icon="el-icon-plus">开具报价单</el-button>
		</div> -->
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column fixed prop="sqId" label="单据编号" width="200"></el-table-column>
			<el-table-column prop="state" label="销售状态" width="100"
				:filters="[{ text: '待确认', value: 0 }, { text: '已出库', value: 1 },{ text: '询价审核失败', value: -1 }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.state)"
					  disable-transitions>{{scope.row.state}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed prop="agent" label="报价经手人" width="150"></el-table-column>
			<el-table-column prop="customer" label="客户" width="200"></el-table-column>
			<el-table-column prop="deliveryAgent" label="出货经手人" width="200"></el-table-column>
			<el-table-column prop="deliveryAgentDate" label="出货时间" width="200"></el-table-column>
			<el-table-column prop="deliveryNote" label="出货备注" width="200"></el-table-column>
			<el-table-column prop="deliveryTotal" label="出货金额" width="200"></el-table-column>
			<el-table-column prop="saleType" label="销售类型" width="150"></el-table-column>
			<el-table-column prop="invoiceType" label="发票类型" width="150"></el-table-column>
			<el-table-column prop="settlementWay" label="结算方式" width="150"></el-table-column>
			<el-table-column prop="total" label="金额" width="100"></el-table-column>
			<el-table-column prop="agentDate" label="发生日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="150">
				<template #default="scope">
					<el-button
					  @click.native.prevent="seeBtnEvent(scope.row)"
					  type="primary"
					  size="small">
					  详细
					</el-button>
					<el-button
						v-show="scope.row.state=='待发货'?true:false"
					  @click.native.prevent="deliveryBtnEvnet(scope.row)"
					  type="warning"
					  size="small">
					  发货
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写销售出库单信息"
			  v-model="deliveryDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <DeliveryForm ref="DeliveryFormData" 
				:isDelivery="isDelivery"
				:propsFormData="propsFormData"></DeliveryForm>
			  <template #footer>
				<span>
					<el-button @click="deliveryDialogVisible = false">取 消</el-button>
				  <el-button type="success" @click="entryDeivleryEnvent">确定出库</el-button>
				  
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let deliveryDialogVisible = false;
let tableData = [];
let isFristShowDeliveryDialog = true;	//是否第一次显示弹框
let deliveryDialogTitle = "";
let isDelivery = false;
import SalesSearchForm from '@/components/Sales/SalesSearchForm.vue';
import DeliveryForm from '@/components/Sales/DeliveryForm.vue';
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Delivery',
	components:{
		SalesSearchForm,DeliveryForm
	},
	data(){
		return{
			tableData,deliveryDialogVisible,isFristShowDeliveryDialog,
			isDelivery
		}
	},
	methods:{
		//点击查看按钮事件
		seeBtnEvent(row){
			this.isDelivery = false;
			this.propsFormData = row;
			this.deliveryDialogTitle = "销售出库单信息";
			if(!this.isFristShowDeliveryDialog){
				this.$refs.DeliveryFormData.queryFun(this.propsFormData.sqId);	
			}
			this.showQuotationDialog();
		},
		//点击发货按钮事件
		deliveryBtnEvnet(row){
			this.isDelivery = true;
			this.propsFormData = row;
			
			this.deliveryDialogTitle = "填写销售出库单信息";
			if(!this.isFristShowDeliveryDialog){
				this.$refs.DeliveryFormData.queryFun(this.propsFormData.sqId);	
			}
			this.showQuotationDialog();
		},
		showQuotationDialog(){
			if(this.isFristShowDeliveryDialog){
				this.isFristShowDeliveryDialog = false;
			}
			this.deliveryDialogVisible = true;
		},
		//点击确定发货按钮
		entryDeivleryEnvent(){
			this.$confirm('确定出库后无法修改，请谨慎操作，是否出货？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定发货按钮");
				let data = this.$refs.DeliveryFormData.formData;
				data.state = '待收货';
				data.salesItemList = this.$refs.DeliveryFormData.tableData;
				data.deliveryTotal = this.$refs.DeliveryFormData.currentDeliveryTotal;
				data.deliveryAgent = this.$store.getters.employeeId;
				delete data.agentDate;
				console.log(data);
				this.submitDeliveryData(data);
				this.deliveryDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		tagType(state){
			if(state == '待发货') return 'warning';
			else return 'success';
		},
		filterType(value, row) {
			return row.type === value;
		},
		//提交发货数据
		submitDeliveryData(data){
			submitDataForPost("/sales-quotation/delivery",data).then(res=>{
				console.log("发货数据返回。。。");
				let result = res.data.data;
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].sqId == data.sqId){
						console.log("匹配到数据");
						result.agentDate = this.tableData[i].agentDate;
						this.tableData[i] = result;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '开具发货单成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '开具发货单失败!'
				});
			});
		},
		//销售出库单页面打开时拉取信息
		getInitInfo(){
			let params = {
				agent:null
			};
			console.log(params);
			GetForParams("/sales-quotation/all-delivery",params).then(res=>{
				this.tableData = res.data.data;
				console.log(this.tableData);
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

<style lang="scss">
@import url("../../assets/css/Public/public.scss");

.contactsDialogClass .el-dialog__body{
	padding:15px 15px 0px 15px;
	height: 470px;
	overflow: auto;
}

</style>
