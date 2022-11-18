ALTER TABLE `base_data_customer` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_member` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_brand` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_category` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_poly` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_property` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_property_item` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_product_saleprop_group` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_store_center` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `base_data_supplier` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `customer_settle_check_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `customer_settle_fee_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `customer_settle_pre_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `customer_settle_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `gen_data_entity` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `gen_data_entity_category` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `op_logs` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_check_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_fee_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_in_item` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_out_item` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_pre_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `settle_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sw_file_box` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sw_online_excel` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_data_dic` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_data_dic_category` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_data_dic_item` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_dept` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_menu` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_notice` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_parameter` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_position` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_role` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `sys_user` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_order_time_line` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_pre_take_stock_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_product_stock_log` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_purchase_order` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_purchase_return` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_receive_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_retail_out_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_retail_return` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_sale_order` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_sale_out_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_sale_return` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_shop` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_stock_cost_adjust_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_take_stock_plan` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
ALTER TABLE `tbl_take_stock_sheet` ADD COLUMN `create_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人ID' AFTER `create_by`;
UPDATE base_data_customer c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_member c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_brand c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_category c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_poly c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_property c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_property_item c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_product_saleprop_group c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_store_center c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE base_data_supplier c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE customer_settle_check_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE customer_settle_fee_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE customer_settle_pre_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE customer_settle_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE gen_data_entity c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE gen_data_entity_category c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE op_logs c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_check_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_fee_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_in_item c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_out_item c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_pre_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE settle_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sw_file_box c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sw_online_excel c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_data_dic c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_data_dic_category c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_data_dic_item c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_dept c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_menu c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_notice c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_parameter c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_position c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_role c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE sys_user c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_order_time_line c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_pre_take_stock_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_product_stock_log c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_purchase_order c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_purchase_return c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_receive_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_retail_out_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_retail_return c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_sale_order c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_sale_out_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_sale_return c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_shop c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_stock_cost_adjust_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_take_stock_plan c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
UPDATE tbl_take_stock_sheet c left join sys_user u on u.id = c.create_by set c.create_by_id = c.create_by, c.create_by = u.name;
ALTER TABLE `base_data_customer` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_member` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_brand` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_category` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_poly` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_property` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_property_item` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_product_saleprop_group` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_store_center` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `base_data_supplier` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `customer_settle_check_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `customer_settle_fee_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `customer_settle_pre_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `customer_settle_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `gen_data_entity` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `gen_data_entity_category` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_check_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_fee_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_in_item` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_out_item` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_pre_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `settle_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sw_file_box` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sw_online_excel` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_data_dic` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_data_dic_category` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_data_dic_item` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_dept` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_menu` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_notice` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_parameter` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_position` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_role` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `sys_user` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_pre_take_stock_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_purchase_order` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_purchase_return` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_receive_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_retail_out_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_retail_return` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_sale_order` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_sale_out_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_sale_return` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_shop` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_stock_cost_adjust_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_take_stock_plan` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
ALTER TABLE `tbl_take_stock_sheet` ADD COLUMN `update_by_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人ID' AFTER `update_by`;
UPDATE base_data_customer c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_member c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_brand c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_category c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_poly c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_property c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_property_item c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_product_saleprop_group c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_store_center c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE base_data_supplier c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE customer_settle_check_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE customer_settle_fee_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE customer_settle_pre_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE customer_settle_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE gen_data_entity c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE gen_data_entity_category c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_check_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_fee_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_in_item c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_out_item c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_pre_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE settle_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sw_file_box c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sw_online_excel c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_data_dic c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_data_dic_category c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_data_dic_item c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_dept c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_menu c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_notice c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_parameter c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_position c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_role c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE sys_user c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_pre_take_stock_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_purchase_order c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_purchase_return c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_receive_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_retail_out_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_retail_return c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_sale_order c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_sale_out_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_sale_return c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_shop c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_stock_cost_adjust_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_take_stock_plan c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;
UPDATE tbl_take_stock_sheet c left join sys_user u on u.id = c.update_by set c.update_by_id = c.update_by, c.update_by = u.name;