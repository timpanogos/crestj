USE interface_repository;

CREATE TABLE `interface` (
  `ifacePk` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'interface primary autoincrementing key of interfaces',
  `iid` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'The globally unique Interface ID',
  `xml` text CHARACTER SET utf8 COMMENT 'The interface source XML',
  `version` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT 'interface version',
  `repotype` varchar(128) CHARACTER SET utf8 NOT NULL COMMENT 'The repository type (i.e. opendof, allseen)',
  `submitterFk` bigint(10) unsigned NOT NULL COMMENT 'The creators Fk',
  `groupFk` bigint(10) unsigned DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Time of first entry',
  `modifiedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'The last time the xml column was modified',
  `published` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Boolean flag indicating if the interface is working or published',
  PRIMARY KEY (`ifacePk`),
  UNIQUE KEY `iid_index` (`iid`,`version`),
  KEY `creator-fk` (`submitterFk`),
  KEY `interface_groupFk` (`groupFk`),
  CONSTRAINT `interface_groupFk` FOREIGN KEY (`groupFk`) REFERENCES `submitter` (`submitterPk`),
  CONSTRAINT `interface_submitterFk` FOREIGN KEY (`submitterFk`) REFERENCES `submitter` (`submitterPk`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='The interface xml containment table';


insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (1,'[63:{01020305}]','<?xml version="1.0" encoding="UTF-8"?>
<interface 
    xmlns="http://opendof.org/schema/interface-repository" 
    xmlns:md="http://opendof.org/schema/interface-repository-meta" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd" 
    iid="[63:{01020305}]">
	<md:code-name>cn_Full_Interface2</md:code-name>
	<md:display-name xml:lang="en">dn_Full Interface2</md:display-name>
	<md:description xml:lang="en">desc_A test interface with one of everything2</md:description>
	<typedefs>
		<boolean type-id="0">
			<md:code-name>cn_boolean</md:code-name>
			<md:known-value value="true" xml:lang="en">kv_on2</md:known-value>
			<md:known-value value="false" xml:lang="en">kv_off2</md:known-value>
			<md:display-name xml:lang="en">dn_boolean2</md:display-name>
			<md:description xml:lang="en">desc_Boolean Type2</md:description>
		</boolean>
	</typedefs>
	<properties>
		<property item-id="0" type-ref= "0" read="true" write="true">
				<md:code-name>cn_booleanProperty</md:code-name>
				<md:display-name xml:lang="en">dn_booleanProperty</md:display-name>
				<md:description xml:lang="en">desc_Boolean Property</md:description>
		</property>
	</properties>
	<methods>
	<!-- methods comment -->
		<method item-id="1">
			<md:code-name>cn_method1</md:code-name>
			<md:display-name xml:lang="en">dn_method</md:display-name>
			<md:description xml:lang="en">desc_Method</md:description>
			<inputs>
				<input type-ref="0">
					<md:code-name>cn_m1i1-boolean</md:code-name>
					<md:display-name xml:lang="en">dn_m1i1-boolean</md:display-name>
					<md:description xml:lang="en">desc_Method1'' first input parameter which is of type Uint8</md:description>
				</input>
			</inputs>
			<outputs>
				<output type-ref="0">
					<md:code-name>cn_m1o2-boolean</md:code-name>
					<md:display-name xml:lang="en">dn_m1o2-boolean</md:display-name>
					<md:description xml:lang="en">desc_Method'' second output parameter which is of type Int8</md:description>
				</output>
			</outputs>
		</method>
	</methods>
	<events>
	<!-- events comment -->
		<event item-id="2">
			<md:code-name>cn_event1</md:code-name>
			<md:display-name xml:lang="en">dn_event1</md:display-name>
			<md:description xml:lang="en">desc_Event 1</md:description>
			<outputs>
				<output type-ref="0">
					<md:code-name>cn_event1o1</md:code-name>
					<md:display-name xml:lang="en">dn_event1o1</md:display-name>
					<md:description xml:lang="en">desc_Event 1''s output 1 which is our structure</md:description>
				</output>
			</outputs>
		</event>
	</events>
	<exceptions>
	<!-- events comment -->
		<exception item-id="3">
			<md:code-name>cn_exception1</md:code-name>
			<md:display-name xml:lang="en">dn_exception1</md:display-name>
			<md:description xml:lang="en">desc_Exception 1</md:description>
			<outputs>
				<output type-ref="0"/>
			</outputs>
		</exception>
	</exceptions>
</interface>
','1','opendof',10,null,'2015-09-21 17:56:00','2015-09-21 17:56:00',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (2,'[63:{01020304}]','<?xml version="1.0" encoding="UTF-8"?>
<!-- 
	version required immutable
	iid required immutable
	all elements are sequence and must be in the order shown here
	all "metablocks" are optional and if given are as follows:
		code-name elements are required and mutable.  Only one
		semantics elements are required and mutable. Only one
		display-name and description are optional and mutable
		multiple display-name and descriptions can be given, but only one per language
-->
<interface 
    xmlns="http://opendof.org/schema/interface-repository" 
    xmlns:md="http://opendof.org/schema/interface-repository-meta" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd" 
    iid="[63:{01020304}]">
	<!-- code-name required mutable, only one -->
	<md:code-name>cn_Full_Interface</md:code-name>
	<!-- display-name optional mutable, one per language-->
	<md:display-name xml:lang="en">dn_Full Interface</md:display-name>
	<md:display-name xml:lang="ja">ã‚¨ã‚¢ã‚³ãƒ³ãƒ‡ã‚£ã‚·ãƒ§ãƒŠãƒ¼ã®çŠ¶æ…‹ï¼Ž</md:display-name>
	<!-- description optional mutable, one per language-->
	<md:description xml:lang="en">desc_A test interface with one of everything</md:description>
	<md:description xml:lang="ja">ã‚¨ã‚¢ã‚³ãƒ³ãƒ‡ã‚£ã‚·ãƒ§ãƒŠãƒ¼ã®çŠ¶æ…‹ï¼Ž</md:description>
	<!-- 
			typedefs optional
		-->
	<!-- zero to many typedef 
				for all typedef within typedefs
					typeid is required, immutable and must be unique within typedefs
				for all dof types within typedef''s 
					min and max ranging is optional on all numerical types.
			-->
	<typedefs>
		<!-- encoding, length required and immutable -->
		<string type-id="0" encoding="3" length="256">
			<md:code-name>cn_string</md:code-name>
			<md:display-name xml:lang="en">dn_string</md:display-name>
			<md:display-name xml:lang="ja">dn_æ–‡å­—åˆ—</md:display-name>
			<md:description xml:lang="en">desc_String Type</md:description>
			<md:description xml:lang="ja">DESC -æ–‡å­—åˆ—åž‹</md:description>
		</string>
		<boolean type-id="1">
			<md:code-name>cn_boolean</md:code-name>
			<md:known-value value="true" xml:lang="en">kv_on</md:known-value>
			<md:known-value value="false" xml:lang="en">kv_off</md:known-value>
			<md:known-value value="true" xml:lang="ja">KV1-ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="false" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_boolean</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Boolean Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</boolean>
		<uint8 type-id="2" min="1" max="10">
			<md:code-name>cn_uint8</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_unit8</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_UInt8 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>miles</unit>
		</uint8>
		<uint16 type-id="3" min="1" max="10">
			<md:code-name>cn_uint16</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_uint16</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Uint16 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>yards</unit>
		</uint16>
		<uint32 type-id="4" min="1" max="10">
			<md:code-name>cn_uint32</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_uint32</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Uint32 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>feet</unit>
		</uint32>
		<uint64 type-id="5" min="1" max="10">
			<md:code-name>cn_uint64</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_uint64</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Uint64 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>inches</unit>
		</uint64>
		<int8 type-id="6" min="1" max="10">
			<md:code-name>cn_int8</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_int8</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Int8 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>miles</unit>
		</int8>
		<int16 type-id="7" min="1" max="10">
			<md:code-name>cn_int16</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_int16</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Int16 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>yards</unit>
		</int16>
		<int32 type-id="8" min="1" max="10">
			<md:code-name>cn_int32</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_int32</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Int32 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>feet</unit>
		</int32>
		<int64 type-id="9" min="1" max="10">
			<md:code-name>cn_int64</md:code-name>
			<md:known-value value="2" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_int64</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Int64 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>inches</unit>
		</int64>
		<float32 type-id="10" min="1.0" max="10.0">
			<md:code-name>cn_float32</md:code-name>
			<md:known-value value="2.0" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4.0" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6.0" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2.0" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4.0" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6.0" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_float32</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_float32 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>inches</unit>
		</float32>
		<float64 type-id="11" min="1.0" max="10.0">
			<md:code-name>cn_float64</md:code-name>
			<md:known-value value="2.0" xml:lang="en">kv_one</md:known-value>
			<md:known-value value="4.0" xml:lang="en">kv_two</md:known-value>
			<md:known-value value="6.0" xml:lang="en">kv_three</md:known-value>
			<md:known-value value="2.0" xml:lang="ja">VK1ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="4.0" xml:lang="ja">KV2_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:known-value value="6.0" xml:lang="ja">KV3_ãƒ–ãƒ¼ãƒ«</md:known-value>
			<md:display-name xml:lang="en">dn_float64</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_float64 Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<unit>inches</unit>
		</float64>
		<datetime type-id="12">
			<md:code-name>cn_datetime</md:code-name>
			<md:display-name xml:lang="en">dn_datetime</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Datetime Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</datetime>
		<!-- 
			min-length optional, length required, both immutable, min set to length if not given 
			typeref is required and immutable.  The typeref values must match a typedef ids in this typedefs block 
		-->
		<array type-id="13"  type-ref="0" min-length="3" length="10">
				<md:code-name>cn_stringarray</md:code-name>
				<md:display-name xml:lang="en">dn_stringarray</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_Array of String Type</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</array>
		<structure type-id="15">
			<md:code-name>cn_structure</md:code-name>
			<md:display-name xml:lang="en">dn_structure</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Structure Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<!-- one to many field''s required.  The number of field''s and their values are immutable.  The type-ref values must match a typedef ids in this typedefs block -->
			<field type-ref="18">
				<md:code-name>cn_selfReference</md:code-name>
				<md:display-name xml:lang="en">dn_selfReference</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_Nullable Self Reference</md:description>
				<md:description xml:lang="ja">DESC - NULLå¯èƒ½ã‚»ãƒ«ãƒ•ãƒªãƒ•ã‚¡ãƒ¬ãƒ³ã‚¹</md:description>
			</field >
			<field  type-ref="0">
				<md:code-name>cn_firstName</md:code-name>
				<md:display-name xml:lang="en">dn_firstName</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_The First Name</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			</field >
			<field  type-ref="1">
				<md:code-name>cn_qualified</md:code-name>
				<md:display-name xml:lang="en">dn_qualified</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_Boolean, is firstName qualidfied</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			</field >
			<field  type-ref="2">
				<md:code-name>cn_age</md:code-name>
				<md:display-name xml:lang="en">dn_age</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_Age of firstname</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			</field >
		</structure>
		<uuid type-id="16">
			<md:code-name>cn_guid</md:code-name>
			<md:display-name xml:lang="en">dn_guid</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Guid Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</uuid>
		<!-- min-length optional, length required, both immutable, min set to length if not given -->
		<blob type-id="17" min-length="3" length="10">
			<md:code-name>cn_blob</md:code-name>
			<md:display-name xml:lang="en">dn_blob</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Blob Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</blob>
			<!-- type-ref is required and immutable.  It must match a typedef id in this typedefs block -->
		<nullable type-id="18"  type-ref="15">
			<md:code-name>cn_nullableStructPointer</md:code-name>
			<md:display-name xml:lang="en">dn_nullableStructPointer</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Nullable Structure Pointer Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</nullable>
		<oid type-id="19">
			<md:code-name>cn_oid</md:code-name>
			<md:display-name xml:lang="en">dn_oid</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Oid Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</oid>
		<iid type-id="20">
			<md:code-name>cn_iid</md:code-name>
			<md:display-name xml:lang="en">dn_iid</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Iid Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</iid>
		<nullable type-id="21"  type-ref="0">
			<md:code-name>cn_nullableStringPointer</md:code-name>
			<md:display-name xml:lang="en">dn_nullableStringPointer</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Nullable String Pointer Type</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</nullable>
	</typedefs>
	<!-- 
			properties optional
		-->
	<properties>
		<!-- 
				zero to many properties 
				itemid required immutable and must be unique within interfaces
				read, write required and immutable
				type-ref ref must match a typedef id from the typedefs section
			-->
		<property item-id="0" type-ref= "0" read="true" write="true">
				<md:code-name>cn_stringProperty</md:code-name>
				<md:display-name xml:lang="en">dn_stringProperty</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_String Property</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</property>
		<property item-id="1" type-ref= "1" read="true" write="true">
				<md:code-name>cn_booleanProperty</md:code-name>
				<md:display-name xml:lang="en">dn_booleanProperty</md:display-name>
				<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
				<md:description xml:lang="en">desc_Boolean Property</md:description>
				<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</property>
	</properties>
	<!-- 
			methods optional
		-->
	<methods>
		<!-- 
				zero to many methods 
				itemid required immutable and must be unique within interfaces
				all type-ref ref''s must match a typedef id from the typedefs section
			-->
		<method item-id="2">
			<md:code-name>cn_method1</md:code-name>
			<md:display-name xml:lang="en">dn_method1</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Method 1</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<!-- inputs optional -->
			<inputs>
				<!-- zero to many input type-ref''s, each could have metablocks -->
				<input type-ref="2">
					<md:code-name>cn_m1i1-age</md:code-name>
					<md:display-name xml:lang="en">dn_m1i1-age</md:display-name>
					<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
					<md:description xml:lang="en">desc_Method1'' first input parameter which is of type Uint8</md:description>
					<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
				</input>
				<input type-ref="3"/>
				<input type-ref="4"/>
			</inputs>
			<!-- outputs optional -->
			<outputs>
				<!-- zero to many output type-ref''s, each could have metablocks -->
				<output type-ref="5"/>
				<output type-ref="6">
					<md:code-name>cn_m1o2-age</md:code-name>
					<md:display-name xml:lang="en">dn_m1o2-age</md:display-name>
					<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
					<md:description xml:lang="en">desc_Method1'' second output parameter which is of type Int8</md:description>
					<md:description xml:lang="ja">ã‚¿ã‚¤ãƒ—ã®INT8ã‚ã‚‹DESC -æ–¹æ³•1 ã€Œç¬¬äºŒã®å‡ºåŠ›ãƒ‘ãƒ©ãƒ¡ãƒ¼ã‚¿</md:description>
				</output>
				<output type-ref="7"/>
			</outputs>
		</method>
		<method item-id="8">
			<md:code-name>cn_method2</md:code-name>
			<md:display-name xml:lang="en">dn_method2</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_method 2</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<inputs>
				<input type-ref="9"/>
				<input type-ref="10"/>
				<input type-ref="11"/>
			</inputs>
			<outputs>
				<output type-ref="12"/>
				<output type-ref="13"/>
				<output type-ref="15"/>
			</outputs>
		</method>
	</methods>
	<!-- 
			events required 
		-->
	<events>
		<!-- 
				zero to many events 
				itemid required immutable and must be unique within interfaces
				all typeref ref''s must match a typedef id from the typedefs section
			-->
		<event item-id="4">
			<md:code-name>cn_event1</md:code-name>
			<md:display-name xml:lang="en">dn_event1</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Event 1</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<!-- outputs optional -->
			<outputs>
				<!-- zero to many output typeref''s, each typeref could have a metablock  -->
				<output type-ref="15">
					<md:code-name>cn_event1o1</md:code-name>
					<md:display-name xml:lang="en">dn_event1o1</md:display-name>
					<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
					<md:description xml:lang="en">desc_Event 1''s output 1 which is our structure</md:description>
					<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
				</output>
				<output type-ref="16"/>
				<output type-ref="17"/>
			</outputs>
		</event>
		<event item-id="5">
			<md:code-name>cn_event2</md:code-name>
			<md:display-name xml:lang="en">dn_event2</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Event 2</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
		</event>
	</events>
	<!-- 
			exceptions optional 
		-->
	<exceptions>
		<!-- 
				zero to many exceptions 
				item-id required immutable and must be unique within interfaces
			-->
		<exception item-id="6">
			<md:code-name>cn_exception1</md:code-name>
			<md:display-name xml:lang="en">dn_exception1</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_Exception 1</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<!-- outputs optional -->
			<outputs>
				<!-- zero to many output typeref''s, each typeref could have a metablock  -->
				<output type-ref="18"/>
				<output type-ref="19"/>
				<output type-ref="20"/>
			</outputs>
		</exception>
		<exception item-id="7">
			<md:code-name>cn_exception2</md:code-name>
			<md:display-name xml:lang="en">dn_exception2</md:display-name>
			<md:display-name xml:lang="ja">dn_ãƒ–ãƒ¼ãƒ«</md:display-name>
			<md:description xml:lang="en">desc_exception2</md:description>
			<md:description xml:lang="ja">DESC ã€ãƒ–ãƒ¼ãƒ«åž‹</md:description>
			<outputs>
				<output type-ref="17"/>
				<output type-ref="18"/>
				<output type-ref="19"/>
			</outputs>
		</exception>
	</exceptions>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:31','2015-09-21 18:05:31',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (3,'[1:{01000048}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000048}]">
    <md:code-name>GPSLocation</md:code-name>
    <md:display-name xml:lang="en">GPS Location</md:display-name>
    <md:description xml:lang="en">This interface represents a location in the real world defined as latitude, longitude, and elevation using the WGS84 datum.</md:description>
    <typedefs>
        <float64 type-id="0" min="-90.0" max="90.0">
            <md:code-name>Latitude</md:code-name>
            <md:display-name xml:lang="en">Latitude</md:display-name>
            <md:description xml:lang="en">The latitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) north of the equator.  Negative values represent degrees south of the equator.</unit>
        </float64>
        <float64 type-id="1" min="-180.0" max="180.0">
            <md:code-name>Longitude</md:code-name>
            <md:display-name xml:lang="en">Longitude</md:display-name>
            <md:description xml:lang="en">The longitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) east of the Prime Meridian (Royal Observatory, Greenwich, England).  Negative values represent degrees west of the Prime Meridian.</unit>
        </float64>
        <float64 type-id="2" min="-10000.0" max="10000.0">
            <md:code-name>Elevation</md:code-name>
            <md:display-name xml:lang="en">Elevation</md:display-name>
            <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            <unit>Meters (m) above sea-level.</unit>
        </float64>
        <structure type-id="3">
            <md:code-name>Location</md:code-name>
            <md:display-name xml:lang="en">Location</md:display-name>
            <md:description xml:lang="en">The location consisting of latitude, longitude, and elevation using the WGS84 datum.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">Latitude</md:display-name>
                <md:description xml:lang="en">The latitude component of the GPS location</md:description>
            </field>
            <field type-ref="1">
                <md:display-name xml:lang="en">Longitude</md:display-name>
                <md:description xml:lang="en">The longitude component of the GPS location</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">Elevation</md:display-name>
                <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            </field>
        </structure>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="3" read="true" write="false">
            <md:code-name>location</md:code-name>
            <md:display-name xml:lang="en">location</md:display-name>
            <md:description xml:lang="en">The GPS location.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:34','2015-09-21 18:05:34',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (4,'[1:{01000049}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000049}]">
    <md:code-name>Impact</md:code-name>
    <md:display-name xml:lang="en">Impact</md:display-name>
    <md:description xml:lang="en">This interface is used for impact force measurement and excessive force reporting.</md:description>
    <typedefs>
        <float64 type-id="0" min="-90.0" max="90.0">
            <md:code-name>Latitude</md:code-name>
            <md:display-name xml:lang="en">Latitude</md:display-name>
            <md:description xml:lang="en">The latitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) north of the equator.  Negative values represent degrees south of the equator.</unit>
        </float64>
        <float64 type-id="1" min="-180.0" max="180.0">
            <md:code-name>Longitude</md:code-name>
            <md:display-name xml:lang="en">Longitude</md:display-name>
            <md:description xml:lang="en">The longitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) east of the Prime Meridian (Royal Observatory, Greenwich, England).  Negative values represent degrees west of the Prime Meridian.</unit>
        </float64>
        <float64 type-id="2" min="-10000.0" max="10000.0">
            <md:code-name>Elevation</md:code-name>
            <md:display-name xml:lang="en">Elevation</md:display-name>
            <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            <unit>Meters (m) above sea-level.</unit>
        </float64>
        <structure type-id="3">
            <md:code-name>Location</md:code-name>
            <md:display-name xml:lang="en">Location</md:display-name>
            <md:description xml:lang="en">The location consisting of latitude, longitude, and elevation using the WGS84 datum.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">Latitude</md:display-name>
                <md:description xml:lang="en">The latitude component of the GPS location</md:description>
            </field>
            <field type-ref="1">
                <md:display-name xml:lang="en">Longitude</md:display-name>
                <md:description xml:lang="en">The longitude component of the GPS location</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">Elevation</md:display-name>
                <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            </field>
        </structure>
        <nullable type-id="4" type-ref="3">
            <md:code-name>NullableLocation</md:code-name>
            <md:display-name xml:lang="en">NullableLocation</md:display-name>
            <md:description xml:lang="en">A nullable GPS location.</md:description>
        </nullable>
        <float32 type-id="5" min="-1.0" max="1.0">
            <md:code-name>Direction</md:code-name>
            <md:display-name xml:lang="en">Direction</md:display-name>
            <md:description xml:lang="en">The normalized length of the force vector along the specific axis.</md:description>
        </float32>
        <float64 type-id="6" min="0.0" max="1.7976931348623157E308">
            <md:code-name>Magnitude</md:code-name>
            <md:display-name xml:lang="en">Magnitude</md:display-name>
            <md:description xml:lang="en">The length of the force vector.</md:description>
            <unit>Standard Gravity (g)</unit>
        </float64>
        <structure type-id="7">
            <md:code-name>Force</md:code-name>
            <md:display-name xml:lang="en">Force</md:display-name>
            <md:description xml:lang="en">The force vector, which contains the direction component and the magnitude component.  The direction is the 3-tuple (i.e., {x, y, z}) of a Euclidian unit vector.</md:description>
            <field type-ref="5">
                <md:display-name xml:lang="en">X</md:display-name>
                <md:description xml:lang="en">The normalized force vector direction along the ''X'' axis.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">Y</md:display-name>
                <md:description xml:lang="en">>The normalized force vector direction along the ''Y'' axis.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">Z</md:display-name>
                <md:description xml:lang="en">The normalized force vector direction along the ''Z'' axis.</md:description>
            </field>
            <field type-ref="6">
                <md:display-name xml:lang="en">Magnitude</md:display-name>
                <md:description xml:lang="en">The magnitude of the force vector.</md:description>
            </field>
        </structure>
    </typedefs>
    <properties>
        <property item-id="2" type-ref="6" read="true" write="true">
            <md:code-name>threshold</md:code-name>
            <md:display-name xml:lang="en">threshold</md:display-name>
            <md:description xml:lang="en">The threshold for triggered events.  Any impact that occurs with a force magnitude greater than this threshold, will trigger an ''impact'' event.</md:description>
        </property>
    </properties>
    <events>
        <event item-id="1">
            <md:code-name>impact</md:code-name>
            <md:display-name xml:lang="en">impact</md:display-name>
            <md:description xml:lang="en">An impact has occurred.  The parameters include the date/time and location of the event as well as the force along each axis.</md:description>
            <outputs>
                <output type-ref="4">
                    <md:display-name xml:lang="en">location</md:display-name>
                    <md:description xml:lang="en">Where the impact occurred.</md:description>
                </output>
                <output type-ref="7">
                    <md:display-name xml:lang="en">force</md:display-name>
                    <md:description xml:lang="en">The force vector.</md:description>
                </output>
            </outputs>
        </event>
    </events>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:41','2015-09-21 18:05:41',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (5,'[63:{AA000001}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[63:{AA000001}]">
    <md:code-name>Light</md:code-name>
    <md:display-name xml:lang="en">Light</md:display-name>
    <md:description xml:lang="en">This interface represents any device that can produce light.</md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="100">
            <md:code-name>Brightness</md:code-name>
            <md:display-name xml:lang="en">Brightness</md:display-name>
            <md:description xml:lang="en">The brightness of the produced light, as a percentage of maximum. 0 indicates no light (off), 100 indicates maximum brightness.</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="0" type-ref="0" read="true" write="true">
            <md:code-name>brightness</md:code-name>
            <md:display-name xml:lang="en">Brightness</md:display-name>
            <md:description xml:lang="en">This is the current brightness output by the device. When setting this value on lights with limited (or no) ability to produce partial/dimmed light, the resulting value will be quantized to the ceiling of the next highest light percentage. For example, a light that has only 3 
possible outputs (off/half-on/full-on) would have valid values of 0, 50, and 100. 0 will always turn off the light. Any value of 1-50 would be quantized to 50, and any value 51-100 would be quantized to 100.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:42','2015-09-21 18:05:42',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (6,'[1:{0100004A}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0100004A}]">
    <md:code-name>LocationHistory</md:code-name>
    <md:display-name xml:lang="en">Location History</md:display-name>
    <md:description xml:lang="en">This interface is used to get a location history.</md:description>
    <typedefs>
        <float64 type-id="0" min="-90.0" max="90.0">
            <md:code-name>Latitude</md:code-name>
            <md:display-name xml:lang="en">Latitude</md:display-name>
            <md:description xml:lang="en">The latitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) north of the equator.  Negative values represent degrees south of the equator.</unit>
        </float64>
        <float64 type-id="1" min="-180.0" max="180.0">
            <md:code-name>Longitude</md:code-name>
            <md:display-name xml:lang="en">Longitude</md:display-name>
            <md:description xml:lang="en">The longitude component of the GPS location.</md:description>
            <unit>Degrees (Â°) east of the Prime Meridian (Royal Observatory, Greenwich, England).  Negative values represent degrees west of the Prime Meridian.</unit>
        </float64>
        <float64 type-id="2" min="-10000.0" max="10000.0">
            <md:code-name>Elevation</md:code-name>
            <md:display-name xml:lang="en">Elevation</md:display-name>
            <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            <unit>Meters (m) above sea-level.</unit>
        </float64>
        <structure type-id="3">
            <md:code-name>Location</md:code-name>
            <md:display-name xml:lang="en">Location</md:display-name>
            <md:description xml:lang="en">The location consisting of latitude, longitude, and elevation using the WGS84 datum.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">Latitude</md:display-name>
                <md:description xml:lang="en">The latitude component of the GPS location</md:description>
            </field>
            <field type-ref="1">
                <md:display-name xml:lang="en">Longitude</md:display-name>
                <md:description xml:lang="en">The longitude component of the GPS location</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">Elevation</md:display-name>
                <md:description xml:lang="en">The elevation component of the GPS location.</md:description>
            </field>
        </structure>
        <datetime type-id="4">
            <md:code-name>Timestamp</md:code-name>
            <md:display-name xml:lang="en">Timestamp</md:display-name>
            <md:description xml:lang="en">A date/time timestamp.</md:description>
        </datetime>
        <structure type-id="5">
            <md:code-name>LocationChange</md:code-name>
            <md:display-name xml:lang="en">LocationChange</md:display-name>
            <md:description xml:lang="en">A location change.</md:description>
            <field type-ref="4">
                <md:display-name xml:lang="en">Timestamp</md:display-name>
                <md:description xml:lang="en">When the location was changed.</md:description>
            </field>
            <field type-ref="3">
                <md:display-name xml:lang="en">Location</md:display-name>
                <md:description xml:lang="en">The new location.</md:description>
            </field>
        </structure>
        <array type-id="6" type-ref="5" min-length="0" length="10000">
            <md:code-name>LocationChanges</md:code-name>
            <md:display-name xml:lang="en">LocationChanges</md:display-name>
            <md:description xml:lang="en">An array of location changes.</md:description>
        </array>
        <uint16 type-id="7">
            <md:code-name>Count</md:code-name>
            <md:display-name xml:lang="en">Count</md:display-name>
            <md:description xml:lang="en">A request count.</md:description>
        </uint16>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>recentHistory</md:code-name>
            <md:display-name xml:lang="en">recentHistory</md:display-name>
            <md:description xml:lang="en">Get the recent history.</md:description>
            <inputs>
                <input type-ref="7">
                    <md:code-name>count</md:code-name>
                    <md:display-name xml:lang="en">count</md:display-name>
                    <md:description xml:lang="en">Get at most this many history entries.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="6">
                    <md:code-name>locations</md:code-name>
                    <md:display-name xml:lang="en">locations</md:display-name>
                    <md:description xml:lang="en">The location history results.</md:description>
                </output>
            </outputs>
        </method>
        <method item-id="2">
            <md:code-name>clear</md:code-name>
            <md:display-name xml:lang="en">clear</md:display-name>
            <md:description xml:lang="en">Clears the known location history.</md:description>
        </method>
    </methods>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:42','2015-09-21 18:05:42',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (7,'[1:{0100004B}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0100004B}]">
    <md:code-name>Lockable</md:code-name>
    <md:display-name xml:lang="en">Lockable</md:display-name>
    <md:description xml:lang="en">This interface represents anything that is lockable.</md:description>
    <typedefs>
        <boolean type-id="0">
            <md:code-name>LockState</md:code-name>
            <md:display-name xml:lang="en">LockState</md:display-name>
            <md:description xml:lang="en">The lock state of the item.  True means locked and false means unlocked.</md:description>
        </boolean>
    </typedefs>
    <properties>
        <property item-id="3" type-ref="0" read="true" write="false">
            <md:code-name>isLocked</md:code-name>
            <md:display-name xml:lang="en">isLocked</md:display-name>
            <md:description xml:lang="en">This is the current lock state of the item.</md:description>
        </property>
    </properties>
    <methods>
        <method item-id="1">
            <md:code-name>lock</md:code-name>
            <md:display-name xml:lang="en">lock</md:display-name>
            <md:description xml:lang="en">Lock the item.  This may fail if not currently lockable.  In that case, the Failed exception is thrown.</md:description>
        </method>
        <method item-id="2">
            <md:code-name>unlock</md:code-name>
            <md:display-name xml:lang="en">unlock</md:display-name>
            <md:description xml:lang="en">Unlock the item.  This may fail if the item is not currently unlockable.  In that case, the Failed exception is thrown.</md:description>
        </method>
    </methods>
    <exceptions>
        <exception item-id="4">
            <md:code-name>failed</md:code-name>
            <md:display-name xml:lang="en">failed</md:display-name>
            <md:description xml:lang="en">This exception may occur when the item cannot be locked or unlocked.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:43','2015-09-21 18:05:43',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (8,'org.allseen.LeaderElectionAndStateSync','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/LeaderElectionAndStateSync" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LeaderElectionAndStateSync">
		<description language="en">This is the LeaderElectionAndStateSync interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetChecksumAndModificationTimestamp">
			<arg name="checksumAndTimestamp" type="a(uut)" direction="out"/>
		</method>
		<method name="GetBlob">
			<arg name="blobType" type="u" direction="in"/>
			<arg name="blobType" type="u" direction="out"/>
			<arg name="blob" type="s" direction="out"/>
			<arg name="checksum" type="u" direction="out"/>
			<arg name="timestamp" type="t" direction="out"/>
		</method>
		<method name="Overthrow">
			<arg name="success" type="b" direction="out"/>
		</method>
		<signal name="BlobChanged">
			<arg name="blobType" type="u" direction="out"/>
			<arg name="blob" type="s" direction="out"/>
			<arg name="checksum" type="u" direction="out"/>
			<arg name="timestamp" type="t" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:43','2015-09-21 18:05:43',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (9,'org.allseen.LSF.ControllerService.Lamp','<?xml version="1.0" encoding="UTF-8" ?>
<?xml-stylesheet type="text/xsl" href="html-translation-allseen.xsl"?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.Lamp">
		<description language="en">This is the ControllerService.Lamp interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllLampIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampIDs" type="as" direction="out"/>
		</method>
		<method name="GetLampSupportedLanguages">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="supportedLanguages" type="as" direction="out"/>
		</method>
		<method name="GetLampManufacturer">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="manufacturer" type="s" direction="out"/>
		</method>
		<method name="GetLampName">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="lampName" type="s" direction="out"/>
		</method>
		<method name="SetLampName">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="GetLampDetails">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampDetails" type="a{sv}" direction="out"/>
		</method>
		<method name="GetLampParameters">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampParameters" type="a{sv}" direction="out"/>
		</method>
		<method name="GetLampParametersField">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampParameterFieldName" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampParameterFieldName" type="s" direction="out"/>
			<arg name="lampParameterFieldValue" type="v" direction="out"/>
		</method>
		<method name="GetLampState">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampState" type="a{sv}" direction="out"/>
		</method>
		<method name="GetLampStateField">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampStateFieldName" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampStateFieldName" type="s" direction="out"/>
			<arg name="lampStateFieldValue" type="v" direction="out"/>
		</method>
		<method name="TransitionLampState">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampState" type="a{sv}" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
		</method>
		<method name="PulseLampWithState">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="fromLampState" type="a{sv}" direction="in"/>
			<arg name="toLampState" type="a{sv}" direction="in"/>
			<arg name="period" type="u" direction="in"/>
			<arg name="duration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
		</method>
		<method name="PulseLampWithPreset">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="fromPresetID" type="s" direction="in"/>
			<arg name="toPresetID" type="s" direction="in"/>
			<arg name="period" type="u" direction="in"/>
			<arg name="duration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
		</method>
		<method name="TransitionLampStateToPreset">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="presetID" type="s" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
		</method>
		<method name="TransitionLampStateField">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampStateFieldName" type="s" direction="in"/>
			<arg name="lampStateFieldValue" type="v" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampStateFieldName" type="s" direction="out"/>
		</method>
		<method name="ResetLampState">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
		</method>
		<method name="ResetLampStateField">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampStateFieldName" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampStateFieldName" type="s" direction="out"/>
		</method>
		<method name="GetLampFaults">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampFaults" type="au" direction="out"/>
		</method>
		<method name="ClearLampFault">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="lampFault" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampFault" type="u" direction="out"/>
		</method>
		<method name="GetLampServiceVersion">
			<arg name="lampID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampServiceVersion" type="u" direction="out"/>
		</method>
		<signal name="LampNameChanged">
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampName" type="s" direction="out"/>
		</signal>
		<signal name="LampStateChanged">
			<arg name="lampID" type="s" direction="out"/>
			<arg name="lampState" type="a{sv}" direction="out"/>
		</signal>
		<signal name="LampsFound">
			<arg name="lampIDs" type="as" direction="out"/>
		</signal>
		<signal name="LampsLost">
			<arg name="lampIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:44','2015-09-21 18:05:44',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (10,'org.allseen.LSF.ControllerService.LampGroup','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.LampGroup">
		<description language="en">This is the ControllerService.LampGroup interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllLampGroupIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupIDs" type="as" direction="out"/>
		</method>
		<method name="GetLampGroupName">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="lampGroupName" type="s" direction="out"/>
		</method>
		<method name="SetLampGroupName">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="lampGroupName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreateLampGroup">
			<arg name="lampIDs" type="as" direction="in"/>
			<arg name="lampGroupIDs" type="as" direction="in"/>
			<arg name="lampGroupName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="UpdateLampGroup">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="lampIDs" type="as" direction="in"/>
			<arg name="lampGroupIDs" type="as" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="DeleteLampGroup">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="GetLampGroup">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
			<arg name="lampIDs" type="as" direction="out"/>
			<arg name="lampGroupIDs" type="as" direction="out"/>
		</method>
		<method name="TransitionLampGroupState">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="lampState" type="a{sv}" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="PulseLampGroupWithState">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="fromLampGroupState" type="a{sv}" direction="in"/>
			<arg name="toLampGroupState" type="a{sv}" direction="in"/>
			<arg name="period" type="u" direction="in"/>
			<arg name="duration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="PulseLampGroupWithPreset">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="fromPresetID" type="s" direction="in"/>
			<arg name="toPresetID" type="s" direction="in"/>
			<arg name="period" type="u" direction="in"/>
			<arg name="duration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="TransitionLampGroupStateToPreset">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="presetID" type="s" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="TransitionLampGroupStateField">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="lampGroupStateFieldName" type="s" direction="in"/>
			<arg name="lampGroupStateFieldValue" type="v" direction="in"/>
			<arg name="transitionPeriod" type="u" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
			<arg name="lampGroupStateFieldName" type="s" direction="out"/>
		</method>
		<method name="ResetLampGroupState">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
		</method>
		<method name="ResetLampGroupStateField">
			<arg name="lampGroupID" type="s" direction="in"/>
			<arg name="lampGroupStateFieldName" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampGroupID" type="s" direction="out"/>
			<arg name="lampGroupStateFieldName" type="s" direction="out"/>
		</method>
		<signal name="LampGroupsNameChanged">
			<arg name="lampGroupsIDs" type="as" direction="out"/>
		</signal>
		<signal name="LampGroupsCreated">
			<arg name="lampGroupsIDs" type="as" direction="out"/>
		</signal>
		<signal name="LampGroupsUpdated">
			<arg name="lampGroupsIDs" type="as" direction="out"/>
		</signal>
		<signal name="LampGroupsDeleted">
			<arg name="lampGroupsIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:44','2015-09-21 18:05:44',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (11,'org.allseen.LSF.ControllerService.MasterScene','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.MasterScene">
		<description language="en">This is the ControllerService.MasterScene interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllMasterSceneIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</method>
		<method name="GetMasterSceneName">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="masterSceneName" type="s" direction="out"/>
		</method>
		<method name="SetMasterSceneName">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="masterSceneName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreateMasterScene">
			<arg name="scenes" type="as" direction="in"/>
			<arg name="masterSceneName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
		</method>
		<method name="UpdateMasterScene">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="scenes" type="as" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
		</method>
		<method name="DeleteMasterScene">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
		</method>
		<method name="GetMasterScene">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
			<arg name="scenes" type="as" direction="out"/>
		</method>
		<method name="ApplyMasterScene">
			<arg name="masterSceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="masterSceneID" type="s" direction="out"/>
		</method>
		<signal name="MasterScenesNameChanged">
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="MasterScenesCreated">
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="MasterScenesUpdated">
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="MasterScenesDeleted">
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="MasterScenesApplied">
			<arg name="masterSceneIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:45','2015-09-21 18:05:45',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (12,'org.allseen.LSF.ControllerService.Preset','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.Preset">
		<description language="en">This is the ControllerService.Preset interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetDefaultLampState">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="lampState" type="a{sv}" direction="out"/>
		</method>
		<method name="SetDefaultLampState">
			<arg name="lampState" type="a{sv}" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
		</method>
		<method name="GetAllPresetIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetIDs" type="as" direction="out"/>
		</method>
		<method name="GetPresetName">
			<arg name="presetID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="presetName" type="s" direction="out"/>
		</method>
		<method name="SetPresetName">
			<arg name="presetID" type="s" direction="in"/>
			<arg name="presetName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreatePreset">
			<arg name="lampState" type="a{sv}" direction="in"/>
			<arg name="presetName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
		</method>
		<method name="UpdatePreset">
			<arg name="presetID" type="s" direction="in"/>
			<arg name="lampState" type="a{sv}" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
		</method>
		<method name="DeletePreset">
			<arg name="presetID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
		</method>
		<method name="GetPreset">
			<arg name="presetID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="presetID" type="s" direction="out"/>
			<arg name="lampState" type="a{sv}" direction="out"/>
		</method>
		<signal name="DefaultLampStateChanged"></signal>
		<signal name="PresetsNameChanged">
			<arg name="presetIDs" type="as" direction="out"/>
		</signal>
		<signal name="PresetsCreated">
			<arg name="presetsIDs" type="as" direction="out"/>
		</signal>
		<signal name="PresetsUpdated">
			<arg name="presetsIDs" type="as" direction="out"/>
		</signal>
		<signal name="PresetsDeleted">
			<arg name="presetsIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:46','2015-09-21 18:05:46',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (13,'org.allseen.LSF.ControllerService.PulseEffect','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.PulseEffect">
		<description language="en">This is the ControllerService.PulseEffect interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllPulseEffectIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectIDs" type="as" direction="out"/>
		</method>
		<method name="GetPulseEffectName">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="pulseEffectName" type="s" direction="out"/>
		</method>
		<method name="SetPulseEffectName">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="pulseEffectName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreatePulseEffect">
			<arg name="toLampState" type="a{sv}" direction="in"/>
			<arg name="pulsePeriod" type="u" direction="in"/>
			<arg name="pulseDuration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="fromLampState" type="a{sv}" direction="in"/>
			<arg name="toPresetID" type="s" direction="in"/>
			<arg name="fromPresetID" type="s" direction="in"/>
			<arg name="pulseEffectName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
		</method>
		<method name="UpdatePulseEffect">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="toLampState" type="a{sv}" direction="in"/>
			<arg name="pulsePeriod" type="u" direction="in"/>
			<arg name="pulseDuration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="fromLampState" type="a{sv}" direction="in"/>
			<arg name="toPresetID" type="s" direction="in"/>
			<arg name="fromPresetID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
		</method>
		<method name="DeletePulseEffect">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
		</method>
		<method name="GetPulseEffect">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
			<arg name="toLampState" type="a{sv}" direction="out"/>
			<arg name="pulsePeriod" type="u" direction="out"/>
			<arg name="pulseDuration" type="u" direction="out"/>
			<arg name="numPulses" type="u" direction="out"/>
			<arg name="fromLampState" type="a{sv}" direction="out"/>
			<arg name="toPresetID" type="s" direction="out"/>
			<arg name="fromPresetID" type="s" direction="out"/>
		</method>
		<method name="ApplyPulseEffectOnLamps">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="lampIDs" type="as" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
			<arg name="lampIDs" type="as" direction="out"/>
		</method>
		<method name="ApplyPulseEffectOnLampGroups">
			<arg name="pulseEffectID" type="s" direction="in"/>
			<arg name="lampGroupIDs" type="as" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="pulseEffectID" type="s" direction="out"/>
			<arg name="lampGroupIDs" type="as" direction="out"/>
		</method>
		<signal name="PulseEffectsNameChanged">
			<arg name="pulseEffectIDs" type="as" direction="out"/>
		</signal>
		<signal name="PulseEffectsCreated">
			<arg name="pulseEffectsIDs" type="as" direction="out"/>
		</signal>
		<signal name="PulseEffectsUpdated">
			<arg name="pulseEffectsIDs" type="as" direction="out"/>
		</signal>
		<signal name="PulseEffectsDeleted">
			<arg name="pulseEffectsIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:46','2015-09-21 18:05:46',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (14,'org.allseen.LSF.ControllerService.Scene','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.Scene">
		<description language="en">This is the ControllerService.Scene interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllSceneIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneIDs" type="as" direction="out"/>
		</method>
		<method name="GetSceneName">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="sceneName" type="s" direction="out"/>
		</method>
		<method name="SetSceneName">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="sceneName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreateScene">
			<arg name="transitionlampsLampGroupsToState" type="a(asasa{sv}u)" direction="in"/>
			<arg name="transitionlampsLampGroupsToPreset" type="a(asassu)" direction="in"/>
			<arg name="pulselampsLampGroupsWithState" type="a(asasa{sv}a{sv}uuu)" direction="in"/>
			<arg name="pulselampsLampGroupsWithPreset" type="a(asasssuuu)" direction="in"/>
			<arg name="sceneName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<method name="UpdateScene">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="transitionlampsLampGroupsToState" type="a(asasa{sv}u)" direction="in"/>
			<arg name="transitionlampsLampGroupsToPreset" type="a(asassu)" direction="in"/>
			<arg name="pulselampsLampGroupsWithState" type="a(asasa{sv}a{sv}uuu)" direction="in"/>
			<arg name="pulselampsLampGroupsWithPreset" type="a(asasssuuu)" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<method name="DeleteScene">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<method name="GetScene">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
			<arg name="transitionlampsLampGroupsToState" type="a(asasa{sv}u)" direction="out"/>
			<arg name="transitionlampsLampGroupsToPreset" type="a(asassu)" direction="out"/>
			<arg name="pulselampsLampGroupsWithState" type="a(asasa{sv}a{sv}uuu)" direction="out"/>
			<arg name="pulselampsLampGroupsWithPreset" type="a(asasssuuu)" direction="out"/>
		</method>
		<method name="ApplyScene">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<signal name="ScenesNameChanged">
			<arg name="sceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="ScenesCreated">
			<arg name="sceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="ScenesUpdated">
			<arg name="sceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="ScenesDeleted">
			<arg name="sceneIDs" type="as" direction="out"/>
		</signal>
		<signal name="ScenesApplied">
			<arg name="sceneIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:47','2015-09-21 18:05:47',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (15,'org.allseen.LSF.ControllerService.SceneElement','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.SceneElement">
		<description language="en">This is the ControllerService.SceneElement interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="GetAllSceneElementIDs">
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</method>
		<method name="GetSceneElementName">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
			<arg name="sceneElementName" type="s" direction="out"/>
		</method>
		<method name="SetSceneElementName">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="sceneElementName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
			<arg name="language" type="s" direction="out"/>
		</method>
		<method name="CreateSceneElement">
			<arg name="SceneElement" type="asass" direction="in"/>
			<arg name="sceneElementName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
		</method>
		<method name="UpdateSceneElement">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="SceneElement" type="asass" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
		</method>
		<method name="DeleteSceneElement">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
		</method>
		<method name="GetSceneElement">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
			<arg name="SceneElement" type="asass" direction="out"/>
		</method>
		<method name="ApplySceneElement">
			<arg name="sceneElementID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneElementID" type="s" direction="out"/>
		</method>
		<signal name="SceneElementsNameChanged">
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</signal>
		<signal name="SceneElementsCreated">
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</signal>
		<signal name="SceneElementsUpdated">
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</signal>
		<signal name="SceneElementsDeleted">
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</signal>
		<signal name="SceneElementsApplied">
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:47','2015-09-21 18:05:47',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (16,'org.allseen.LSF.ControllerService.SceneWithSceneElements','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.SceneWithSceneElements">
		<description language="en">This is the ControllerService.SceneWithSceneElements interface</description>
		<method name="CreateSceneWithSceneElements">
			<arg name="sceneElementIDs" type="as" direction="in"/>
			<arg name="sceneName" type="s" direction="in"/>
			<arg name="language" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<method name="UpdateSceneWithSceneElements">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="sceneElementIDs" type="as" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
		</method>
		<method name="GetSceneWithSceneElements">
			<arg name="sceneID" type="s" direction="in"/>
			<arg name="responseCode" type="u" direction="out"/>
			<arg name="sceneID" type="s" direction="out"/>
			<arg name="sceneElementIDs" type="as" direction="out"/>
		</method>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:48','2015-09-21 18:05:48',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (17,'org.allseen.LSF.ControllerService.TransitionEffect','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService.TransitionEffect">
      <description language="en">This is the ControllerService.TransitionEffect interface</description>
      <property name="Version" type="u" access="read"/>
        <method name="GetAllTransitionEffectIDs">
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectIDs" type="as" direction="out"/>
        </method>
        <method name="GetTransitionEffectName">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="language" type="s" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
          <arg name="language" type="s" direction="out"/>
          <arg name="transitionEffectName" type="s" direction="out"/>
        </method>
        <method name="SetTransitionEffectName">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="transitionEffectName" type="s" direction="in"/>
          <arg name="language" type="s" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
          <arg name="language" type="s" direction="out"/>
        </method>
        <method name="CreateTransitionEffect">
          <arg name="lampState" type="a{sv}" direction="in"/>
          <arg name="presetID" type="s" direction="in"/>
          <arg name="transitionPeriod" type="u" direction="in"/>
          <arg name="transitionEffectName" type="s" direction="in"/>
          <arg name="language" type="s" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
        </method>
        <method name="UpdateTransitionEffect">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="lampState" type="a{sv}" direction="in"/>
          <arg name="presetID" type="s" direction="in"/>
          <arg name="transitionPeriod" type="u" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
        </method>
        <method name="DeleteTransitionEffect">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
        </method>
        <method name="GetTransitionEffect">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
          <arg name="lampState" type="a{sv}" direction="out"/>
          <arg name="presetID" type="s" direction="out"/>
          <arg name="transitionPeriod" type="u" direction="out"/>
        </method>
        <method name="ApplyTransitionEffectOnLamps">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="lampIDs" type="as" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
          <arg name="lampIDs" type="as" direction="out"/>
        </method>
        <method name="ApplyTransitionEffectOnLampGroups">
          <arg name="transitionEffectID" type="s" direction="in"/>
          <arg name="lampGroupIDs" type="as" direction="in"/>
          <arg name="responseCode" type="u" direction="out"/>
          <arg name="transitionEffectID" type="s" direction="out"/>
          <arg name="lampGroupIDs" type="as" direction="out"/>
        </method>
        <signal name="TransitionEffectsNameChanged">
          <arg name="transitionEffectIDs" type="as" direction="out"/>
        </signal>
        <signal name="TransitionEffectsCreated">
          <arg name="transitionEffectsIDs" type="as" direction="out"/>
        </signal>
        <signal name="TransitionEffectsUpdated">
          <arg name="transitionEffectsIDs" type="as" direction="out"/>
        </signal>
        <signal name="TransitionEffectsDeleted">
          <arg name="transitionEffectsIDs" type="as" direction="out"/>
        </signal>
      </interface>
    </node>
','1','allseen',10,null,'2015-09-21 18:05:48','2015-09-21 18:05:48',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (18,'org.allseen.LSF.ControllerService','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/ControllerService" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.ControllerService">
		<description language="en">This is the ControllerService interface</description>
		<property name="Version" type="u" access="read"/>
		<method name="LightingResetControllerService">
			<arg name="responseCode" type="u" direction="out"/>
		</method>"
		<method name="GetControllerServiceVersion">
			<arg name="version" type="u" direction="out"/>
		</method>"
		<signal name="ControllerServiceLightingReset"></signal>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:49','2015-09-21 18:05:49',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (19,'org.allseen.LSF.LampDetails','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/Lamp" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.LampDetails">
		<description language="en">This is the LampDetails interface</description>
		<property name="Version" type="u" access="read">
			<description language="en">This is the property Version</description>
		</property>
		<property name="Make" type="u" access="read"/>
		<property name="Model" type="u" access="read"/>
		<property name="Type" type="u" access="read"/>
		<property name="LampType" type="u" access="read"/>
		<property name="LampBaseType" type="u" access="read"/>
		<property name="LampBeamAngle" type="u" access="read"/>
		<property name="Dimmable" type="b" access="read"/>
		<property name="Color" type="b" access="read"/>
		<property name="VariableColorTemp" type="b" access="read"/>
		<property name="HasEffects" type="b" access="read"/>
		<property name="MinVoltage" type="u" access="read"/>
		<property name="MaxVoltage" type="u" access="read"/>
		<property name="Wattage" type="u" access="read"/>
		<property name="IncandescentEquivalent" type="u" access="read"/>
		<property name="MaxLumens" type="u" access="read"/>
		<property name="MinTemperature" type="u" access="read"/>
		<property name="MaxTemperature" type="u" access="read"/>
		<property name="ColorRenderingIndex" type="u" access="read"/>
		<property name="LampID" type="s" access="read"/>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:49','2015-09-21 18:05:49',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (20,'org.allseen.LSF.LampParameters','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/Lamp" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.LampParameters">
		<description language="en">This is the LampParameters interface</description>
		<property name="Version" type="u" access="read"/>
		<property name="Energy_Usage_Milliwatts" type="u" access="read"/>
		<property name="Brightness_Lumens" type="u" access="read"/>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:50','2015-09-21 18:05:50',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (21,'org.allseen.LSF.LampService','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/Lamp" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.LampService">
		<description language="en">This is the LampService interface</description>
		<property name="Version" type="u" access="read"/>
		<property name="LampServiceVersion" type="u" access="read"/>
		<method name="ClearLampFault">
			<arg name="LampFaultCode" type="u" direction="in"/>
			<arg name="LampResponseCode" type="u" direction="out"/>
			<arg name="LampFaultCode" type="u" direction="out"/>
		</method>
		<property name="LampFaults" type="au" access="read"/>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:51','2015-09-21 18:05:51',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (22,'org.allseen.LSF.LampState','<?xml version="1.0" encoding="UTF-8" ?>
<node 
	name="/org/allseen/LSF/Lamp" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.allseen.LSF.LampState">
		<description language="en">This is the LampState interface</description>
		<property name="Version" type="u" access="read">
			<description language="en">This is the property Version</description>
		</property>
		<method name="TransitionLampState">
			<description language="en">This is the TransitionLampState method</description>
			<arg name="Timestamp" type="t" direction="in"/>
			<arg name="NewState" type="a{sv}" direction="in"/>
			<arg name="TransitionPeriod" type="u" direction="in"/>
			<arg name="LampResponseCode" type="u" direction="out"/>
		</method>
		<method name="ApplyPulseEffect">
			<description language="en">This is the ApplyPulseEffect method</description>
			<arg name="FromState" type="a{sv}" direction="in"/>
			<arg name="ToState" type="a{sv}" direction="in"/>
			<arg name="period" type="u" direction="in"/>
			<arg name="duration" type="u" direction="in"/>
			<arg name="numPulses" type="u" direction="in"/>
			<arg name="timestamp" type="t" direction="in"/>
			<arg name="LampResponseCode" type="u" direction="out"/>
		</method>
		<signal name="LampStateChanged">
			<description language="en">This is the LampStateChanged signal</description>
			<arg name="LampID" type="s"/>
		</signal>
		<property name="OnOff" type="b" access="readwrite"/>
		<property name="Hue" type="u" access="readwrite"/>
		<property name="Saturation" type="u" access="readwrite"/>
		<property name="ColorTemp" type="u" access="readwrite"/>
		<property name="Brightness" type="u" access="readwrite"/>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:51','2015-09-21 18:05:51',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (23,'org.freedesktop.DBus.Properties','<?xml version="1.0" encoding="UTF-8"?>
<node 
	name="/org/allseen/LSF/Lamp" 
	xmlns="https://allseenalliance.org/schemas/extended_introspect" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://allseenalliance.org/schemas/extended_introspect https://allseenalliance.org/schemas/extended_introspect.xsd">
	<interface name="org.freedesktop.DBus.Properties">
		<description language="en">This is the DBus.Properties interface</description>
		<method name="Get">
			<arg type="s" direction="in"/>
			<arg type="s" direction="in"/>
			<arg type="v" direction="out"/>
		</method>
		<method name="Set">
			<arg type="s" direction="in"/>
			<arg type="s" direction="in"/>
			<arg type="v" direction="in"/>
		</method>
		<method name="GetAll">
			<arg type="s" direction="in"/>
			<arg type="a{sv}" direction="out"/>
		</method>
	</interface>
</node>
','1','allseen',10,null,'2015-09-21 18:05:52','2015-09-21 18:05:52',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (24,'[1:{01000054}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000054}]">
    <md:code-name>Temporary Password Credentials</md:code-name>
    <md:display-name xml:lang="en">Temporary Password Credentials</md:display-name>
    <md:description xml:lang="en">This interface is used to request temporary password credentials. Temporary credentials are DOF credentials that are only valid for a short time, primarily designed to allow access in conjuction with a presentation or demo. The interface has reduced security requirements because of the nature of the credentials. The object id of the object providing this interface should be a known id for the presentation or demo.</md:description>
    <typedefs>
        <oid type-id="1">
            <md:code-name>Identity</md:code-name>
            <md:display-name xml:lang="en">Identity</md:display-name>
            <md:description xml:lang="en">An object id used as an identity.</md:description>
        </oid>
        <string type-id="2" encoding="3" length="32">
            <md:code-name>Password</md:code-name>
            <md:display-name xml:lang="en">Password</md:display-name>
            <md:description xml:lang="en">A password string.</md:description>
        </string>
        <blob type-id="3" min-length="1" length="4096">
            <md:code-name>Permission Set</md:code-name>
            <md:display-name xml:lang="en">Permission Set</md:display-name>
            <md:description xml:lang="en">A blob representing a permissions set.</md:description>
        </blob>
        <datetime type-id="4">
            <md:code-name>Expiry</md:code-name>
            <md:display-name xml:lang="en">Expiry</md:display-name>
            <md:description xml:lang="en">An expiration date.</md:description>
        </datetime>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>requestCredentials</md:code-name>
            <md:display-name xml:lang="en">requestCredentials</md:display-name>
            <md:description xml:lang="en">Request temporary credentials. This method will return temporary credentials that will expire at the specified expiration date. 
	If the same unique identifier is supplied this method will return the same credentials up to the expiry date.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:code-name>Unique Identifier</md:code-name>
                    <md:display-name xml:lang="en">Unique Identifier</md:display-name>
                    <md:description xml:lang="en">The unique identifier of the device that is requesting credentials. This identifier will be associated with the temporary credentials that are returned.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="1">
                    <md:code-name>Identity</md:code-name>
                    <md:display-name xml:lang="en">Identity</md:display-name>
                    <md:description xml:lang="en">The identity of the credentials. This identity may not match the unique identifier passed as an input value.</md:description>
                </output>
                <output type-ref="2">
                    <md:code-name>Password</md:code-name>
                    <md:display-name xml:lang="en">Password</md:display-name>
                    <md:description xml:lang="en">The password of the credentials. These password should not be encrypted.</md:description>
                </output>
                <output type-ref="3">
                    <md:code-name>Permission Set</md:code-name>
                    <md:display-name xml:lang="en">Permissions Set</md:display-name>
                    <md:description xml:lang="en">The permissions set that is assigned to the temporary credentials. This can be used to set permission on secure components created with the temporary credentials, and to get which object ids can be used for provider and requestor object.</md:description>
                </output>
                <output type-ref="4">
                    <md:code-name>Expiry</md:code-name>
                    <md:display-name xml:lang="en">Expiry</md:display-name>
                    <md:description xml:lang="en">The date when these credentials expire.</md:description>
                </output>
            </outputs>
        </method>
    </methods>
    <exceptions>
        <exception item-id="2">
            <md:code-name>failed</md:code-name>
            <md:display-name xml:lang="en">Failed</md:display-name>
            <md:description xml:lang="en">This exception occurs when credentials could not be created.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:52','2015-09-21 18:05:52',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (25,'[1:{01000023}]','<?xml version="1.0" encoding="UTF-8"?>
<interface 
    xmlns="http://opendof.org/schema/interface-repository" 
    xmlns:md="http://opendof.org/schema/interface-repository-meta" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd "
    iid="[1:{01000023}]">
	<md:code-name>TOASTER</md:code-name>
	<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
	<md:display-name xml:lang="en">Toaster</md:display-name>
	<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
	<md:description xml:lang="en">This interface represents a toaster. The definition includes all the required functionality. This toaster can toast one set of bread at a time.</md:description>
	<typedefs>
		<boolean type-id="0">
			<md:code-name>Powered</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Powered State</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">A power state (on/off). True means ''on''.</md:description>
		</boolean>
		<uint8 type-id="1">
			<md:code-name>Intensity</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Intensity</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">An indication of desired intensity. The larger the number, the more intense.</md:description>
		</uint8>
		<uint8 type-id="2">
			<md:code-name>Mode</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Mode</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">A mode for a toaster. The allowed values are: 0, toast, 1, bagel.</md:description>
		</uint8>
		<blob type-id="3" length="65535">
			<md:code-name>PNGImage</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">PNG Image</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">A PNG image.</md:description>
		</blob>
		<nullable type-id="4" type-ref="3">
			<md:code-name>NullablePNGImage</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Nullable PNG Image</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">A PNG image that also allows a NULL value.</md:description>
		</nullable>
	</typedefs>
	<properties>
		<property item-id="1" type-ref="0" read="true" write="true">
			<md:code-name>powered</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Powered</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">The power state of the toaster.</md:description>
		</property>
		<property item-id="2" type-ref="1" read="true" write="true">
			<md:code-name>intensity</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Desired Intensity</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">The desired intensity of the toasting.</md:description>
		</property>
		<property item-id="3" type-ref="2" read="true" write="true">
			<md:code-name>mode</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Mode</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">The mode the toaster is in.</md:description>
		</property>
	</properties>
	<methods>
		<method item-id="4">
			<md:code-name>cancel</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Cancel</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">Cancel the current toasting.</md:description>
		</method>
		<method item-id="5">
			<md:code-name>setStamp</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Set Stamp</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">Set the stamp to use for future toast. Null indicates no stamp.</md:description>
			<inputs>
				<input type-ref="4">
					<md:code-name>stamp</md:code-name>
					<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
					<md:display-name xml:lang="en">Stamp</md:display-name>
					<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
					<md:description xml:lang="en">The desired stamp, or null.</md:description>
				</input>
			</inputs>
		</method>
	</methods>
	<events>
		<event item-id="6">
			<md:code-name>done</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Done</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">Indicates that a toasting operation has completed.</md:description>
		</event>
		<event item-id="7">
			<md:code-name>burning</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Burning</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">Indicates that the toast is burning.</md:description>
		</event>
	</events>
	<exceptions>
		<exception item-id="8">
			<md:code-name>notToasting</md:code-name>
			<md:display-name xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:display-name>
			<md:display-name xml:lang="en">Not Toasting</md:display-name>
			<md:description xml:lang="ja">ç¿»è¨³å¸Œæœ›</md:description>
			<md:description xml:lang="en">Indicates that the toaster is not toasting, and so a request is not valid.</md:description>
		</exception>
	</exceptions>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:53','2015-09-21 18:05:53',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (26,'[1:{0100004C}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0100004C}]">
    <md:code-name>Weight</md:code-name>
    <md:display-name xml:lang="en">Weight</md:display-name>
    <md:description xml:lang="en">This interface is used for checking the physical weight of an object.</md:description>
    <typedefs>
        <float64 type-id="0" min="0.0" max="1.7976931348623157E308">
            <md:code-name>Weight</md:code-name>
            <md:display-name xml:lang="en">Weight</md:display-name>
            <md:description xml:lang="en">The physical weight of the object.</md:description>
            <unit>Kilograms (kg)</unit>
        </float64>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>weight</md:code-name>
            <md:display-name xml:lang="en">weight</md:display-name>
            <md:description xml:lang="en">The current weight of the object.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',10,null,'2015-09-21 18:05:53','2015-09-21 18:05:53',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (27,'[1:{01000000}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000000}]">
    <md:code-name>TRAINING_BASE</md:code-name>
    <md:display-name xml:lang="ja">ãƒˆãƒ¬ãƒ¼ãƒ‹ãƒ³ã‚°ç”¨ãƒ™ãƒ¼ã‚¹ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹</md:display-name>
    <md:display-name xml:lang="en">Training Base Interface</md:display-name>
    <md:description xml:lang="ja">
        ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã«ã¯ã‚¢ã‚¤ãƒ†ãƒ ãŒå«ã¾ã‚Œã¦ã„ãªã„ã€‚EMITã‚ªãƒ–ã‚¸ã‚§ã‚¯ãƒˆã¯ã€ä»–ã®ãƒˆãƒ¬ãƒ¼ãƒ‹ãƒ³ã‚°ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ä¸Šã§ã‚‚ã‚»ãƒƒã‚·ãƒ§ãƒ³ã‚’æä¾›ã™ã‚‹å ´åˆã€ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã‚’æä¾›ã™ã‚‹ã€‚
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000001">Waveform</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000002">Event Interface</a></li>
   	    </ul>]]>
    </md:description>
    <md:description xml:lang="en">
        This interface contains no items. EMIT objects provide this interface if they also provide a session on other training interfaces.
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000001">Waveform</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000002">Event Interface</a></li>
   	    </ul>]]>
    </md:description>
    <typedefs>
    </typedefs>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:56','2015-09-21 18:05:56',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (28,'[1:{01000022}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000022}]">
    <md:code-name>DATAMANAGER</md:code-name>
    <md:display-name xml:lang="en">Data Manager</md:display-name>
    <md:description xml:lang="en">
        This interface is provided by the data manager. There is a single data manager for each distinct instance of a data platform.
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <tab id=t1>Basics
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0225">Data Sink</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0226">Data Source</a></li>
        </ul>
        <tab id=t1>Snapshot
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0228">Data Snapshot</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000027">Data Snapshot Config</a></li>
        </ul>
        <tab id=t1>Topology
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000028">Data Topology Update</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0227">Data Topology Info</a></li>
        </ul>
        ]]>
    </md:description>
    <typedefs>
        <oid type-id="1">                                                                                               
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID
            </md:display-name>
            <md:description xml:lang="en">Identifier for different actors in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <string type-id="2" encoding="3" length="32767">
            <md:code-name>Notification</md:code-name>
            <md:display-name xml:lang="en">Notification</md:display-name>
            <md:description xml:lang="en"><![CDATA[A notification message that is targetted for a human rather than a program, in HTML format. The text will begin with an ''<html>'' tag and end with an ''</html>'' tag.]]></md:description>
        </string>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>Notify</md:code-name>
            <md:display-name xml:lang="en">Notify</md:display-name>
            <md:description xml:lang="en">Send a notification based on the input parameters. There is no response and no failure mode - it is assumed that a failure to deliver the notification will (internally) cause some other administrative notification.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">deviceID</md:display-name>
                    <md:description xml:lang="en">The device that the notification applies to.</md:description>
                </input>
                <input type-ref="1">
                    <md:display-name xml:lang="en">rootID</md:display-name>
                    <md:description xml:lang="en">The top-most (root) parent that the node applies to. The manager may not be aware of the complete topology, and so may not be able to easily determine an appropriate notification recipient. This parameter offloads the manager by providing the top-most parent, which should be sufficient to determine the appropriate recipient.</md:description>
                </input>
                <input type-ref="2">
                    <md:display-name xml:lang="en">notification</md:display-name>
                    <md:description xml:lang="en">The notification content.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:57','2015-09-21 18:06:55',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (29,'[1:{0225}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0225}]">
    <md:code-name>DATASINK</md:code-name>
    <md:display-name xml:lang="en">Data Sink</md:display-name>
    <md:description xml:lang="en">
        This interface is used by a data source to advertise data, events, and topology to a configured data sink. In addition to defining methods, this interface also defines types that are used as the specification for various persistent storage formats. These storage formats are associated with the IID of this interface and identified with ''[storage]'' in their descriptions. Further, there are unused types defined that correspond to the formatting data blocks before compression. In this case the compressed field is passed on the wire, and the unused type defines the format and contents of the block before compression. These types are identified by putting ''[formatting]'' in their descriptions.
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0226">Data Source</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <iid type-id="18">
            <md:code-name>SinkVersion</md:code-name>
            <md:display-name xml:lang="en">Sink Version</md:display-name>
            <md:description xml:lang="en">[storage] An IID used to identify the version of stored information related to the sink API. Future versions of this interface will need to differentiate between versions. All stored data defined by this interface will begin with this type, allowing it to be read to identify the data format that follows.</md:description>
        </iid>
        <iid type-id="31">
            <md:code-name>SourceVersion</md:code-name>
            <md:display-name xml:lang="en">Source Version</md:display-name>
            <md:description xml:lang="en">[storage] An IID used to identify the version of stored information related to the source IID associated with this sink IID. Future versions of this interface will need to differentiate between versions. All stored data defined by the source interface will begin with this type, allowing it to be read to identify the data format that follows.</md:description>
        </iid>
        <oid type-id="0">
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID</md:display-name>
            <md:description xml:lang="en">An identifier for an actor in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <nullable type-id="32" type-ref="0">
            <md:code-name>OptionalID</md:code-name>
            <md:display-name xml:lang="en">Optional ID</md:display-name>
            <md:description xml:lang="en">An optional ID that is used in cases (for example, parent) where the ID may not exist. NULL indicates that there is no ID.</md:description>
        </nullable>
        <uint32 type-id="1">
            <md:code-name>ValueSetID</md:code-name>
            <md:display-name xml:lang="en">Value Set ID</md:display-name>
            <md:description xml:lang="en">An identifier for the advertised value set, unique to the sink and source involved in the transfer. When used to either request or acknowledge the value set (using the Data Source interface) this identifier uniquely identifies the value set and the sink it is being transferred to.</md:description>
        </uint32>
        <uint32 type-id="20">
            <md:code-name>EventID</md:code-name>
            <md:display-name xml:lang="en">Event ID</md:display-name>
            <md:description xml:lang="en">An identifier for a signaled event, unique to the sink and source involved in the transfer. When used to acknowledge the event (using the Data Source interface) this identifier uniquely identifies the event and the sink the event is being transferred to.</md:description>
        </uint32>
        <uint32 type-id="22">
            <md:code-name>TopologyID</md:code-name>
            <md:display-name xml:lang="en">Topology ID</md:display-name>
            <md:description xml:lang="en">An identifier for a topology update, unique to the sink and source involved in the transfer. When used to acknowledge the update (using the Data Source interface) this identifier uniquely identifies the update and the sink the event is being transferred to.</md:description>
        </uint32>
        <datetime type-id="4">
            <md:code-name>Time</md:code-name>
            <md:display-name xml:lang="en">Time</md:display-name>
            <md:description xml:lang="en">The timestamp used throughout to reference start and end times. Time is always in UTC, as required by the type.</md:description>
        </datetime>
        <uint32 type-id="5">
            <md:code-name>DeltaTime</md:code-name>
            <md:display-name xml:lang="en">Time Quantum</md:display-name>
            <md:description xml:lang="en">The number of milliseconds in each quantum of time used in the value transfer.</md:description>
        </uint32>
        <uint16 type-id="17">
            <md:code-name>Position</md:code-name>
            <md:display-name xml:lang="en">Position</md:display-name>
            <md:description xml:lang="en">The logical position of the device relative to the parent. The parent defines the context/meaning of the positions for its children. A parent may have multiple children with the same position, which may indicate a grouping or relationship between those children. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
        </uint16>
        <nullable type-id="30" type-ref="17">
            <md:code-name>OptionalPosition</md:code-name>
            <md:display-name xml:lang="en">Optional Position</md:display-name>
            <md:description xml:lang="en">An optional position is used in cases where the position may not exist. NULL indicates that there is no position.</md:description>
        </nullable>
        <blob type-id="3" min-length="0" length="65535">
            <md:code-name>ValueSetDefinition</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition</md:display-name>
            <md:description xml:lang="en">A compressed block that defines the properties that are being transferred in the advertisement. The compression algorithm is DEFLATE (RFC 1951). The uncompressed block is an EMIT-marshalled instance of the ''Uncompressed Value Set Definition'' type defined in this interface.</md:description>
        </blob>
        <nullable type-id="34" type-ref="3">
            <md:code-name>OptionalValueSetDefinition</md:code-name>
            <md:display-name xml:lang="en">Optional Value Set Definition</md:display-name>
            <md:description xml:lang="en">[storage] Either a value set definition or a NULL if the definition is not present.</md:description>
        </nullable>
        <uint32 type-id="7">
            <md:code-name>ValueSetDefinitionAlias</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition Alias</md:display-name>
            <md:description xml:lang="en">An alias for the Value Set Definition, assigned by the sink and optionally used by the source.</md:description>
        </uint32>
        <blob type-id="21" min-length="0" length="65535">
            <md:code-name>Event</md:code-name>
            <md:display-name xml:lang="en">Event</md:display-name>
            <md:description xml:lang="en">A compressed block that describes both the event definition that is being transferred in the signal and the event parameters. The compression algorithm is DEFLATE (RFC 1951). The uncompressed block is an EMIT-marshalled instance of the ''Uncompressed Event'' type defined in this interface.</md:description>
        </blob>
        <iid type-id="9">
            <md:code-name>ElementIID</md:code-name>
            <md:display-name xml:lang="en">Element IID</md:display-name>
            <md:description xml:lang="en">[formatting] The IID of a property or event (collectively, element) that can be passed.</md:description>
        </iid>
        <uint16 type-id="10" min="0" max="32767">
            <md:code-name>PropertyItemID</md:code-name>
            <md:display-name xml:lang="en">Property Item ID</md:display-name>
            <md:description xml:lang="en">[formatting] A property item ID.</md:description>
        </uint16>
        <uint16 type-id="25" min="0" max="32767">
            <md:code-name>EventItemID</md:code-name>
            <md:display-name xml:lang="en">Event Item ID</md:display-name>
            <md:description xml:lang="en">[formatting] An event item ID.</md:description>
        </uint16>
        <uint8 type-id="11">
            <md:code-name>PropertyAggregationMethod</md:code-name>
            <md:display-name xml:lang="en">Property Aggregation Method</md:display-name>
            <md:description xml:lang="en">[formatting] A number that identifies the aggregation method used for this property. The following values are defined; any other values are not defined and must be rejected. RAW(0) - No aggregation is done. AVEDEV(1) - The average of the absolute deviations from the mean. AVERAGE(2) - The average (arithmetic mean). GEOMEAN(3) - The geometric mean. MAX(4) - The maximum value. MEDIAN(5) - The number in the middle of the values. MIN(6) - The minimum value. STDDEV(7) - The standard deviation. VAR(8) - The variance. FIRST(9) - The first value in the sequence. LAST(10) - The last value in the sequence.</md:description>
        </uint8>
        <structure type-id="13">
            <md:code-name>PropertyDefinition</md:code-name>
            <md:display-name xml:lang="en">Property Definition</md:display-name>
            <md:description xml:lang="en">[formatting] The definition of a property that will be exchanged.</md:description>
            <field type-ref="9">
                <md:display-name xml:lang="en">iid</md:display-name>
                <md:description xml:lang="en">The IID of the property.</md:description>
            </field>
            <field type-ref="10">
                <md:display-name xml:lang="en">itemID</md:display-name>
                <md:description xml:lang="en">The item ID of the property.</md:description>
            </field>
            <field type-ref="11">
                <md:display-name xml:lang="en">aggregation</md:display-name>
                <md:description xml:lang="en">The aggregation method used.</md:description>
            </field>
            <field type-ref="10">
                <md:display-name xml:lang="en">type</md:display-name>
                <md:description xml:lang="en">The item ID of a property in the reference interface that defines the property type.</md:description>
            </field>
        </structure>
        <array type-id="14" type-ref="13" min-length="0" length="32767">
            <md:code-name>PropertyDefinitionArray</md:code-name>
            <md:display-name xml:lang="en">Property Definition Array</md:display-name>
            <md:description xml:lang="en">[formatting] An variable-length array of property definitions.</md:description>
        </array>
        <blob type-id="15" min-length="5" length="65535">
            <md:code-name>PropertyTypes</md:code-name>
            <md:display-name xml:lang="en">Reference Interface Definition</md:display-name>
            <md:description xml:lang="en">[formatting] A blob containing an interface definition. The interface definition is defined in the OAP protocol specification, PDU OAP.13.2, as everything that follows the opcode byte of the response to the Define PDU.</md:description>
        </blob>
        <structure type-id="16">
            <md:code-name>NonCompressedValueSetDefinition</md:code-name>
            <md:display-name xml:lang="en">Uncompressed Value Set Definition</md:display-name>
            <md:description xml:lang="en">[formatting] A structure containing the uncompressed version of the entire value set definition for transfer. Note that this type is never used, but it defines the uncompressed format of a block that is passed on the wire.</md:description>
            <field type-ref="14">
                <md:display-name xml:lang="en">properties</md:display-name>
                <md:description xml:lang="en">An array of property definitions.</md:description>
            </field>
            <field type-ref="15">
                <md:display-name xml:lang="en">types</md:display-name>
                <md:description xml:lang="en">An interface definition used to store type definitions.</md:description>
            </field>
        </structure>
        <structure type-id="23">
            <md:code-name>RangeMetadata</md:code-name>
            <md:display-name xml:lang="en">Range Metadata</md:display-name>
            <md:description xml:lang="en">This structure defines metadata for data over a range of time.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">deviceID</md:display-name>
                <md:description xml:lang="en">The ID of the device that originated the data.</md:description>
            </field>
            <field type-ref="32">
                <md:display-name xml:lang="en">parentID</md:display-name>
                <md:description xml:lang="en">The ID of the parent of the device, if applicable.  The parent is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
            <field type-ref="30">
                <md:display-name xml:lang="en">position</md:display-name>
                <md:description xml:lang="en">The position of the device relative to the parent, if applicable. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">blockStartTime</md:display-name>
                <md:description xml:lang="en">The time of the first data value in the advertised data block. This time should be aligned on a quantum, and must be rounded to the nearest quantum if it is not.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">blockQuantum</md:display-name>
                <md:description xml:lang="en">The quantum used for each timestamp in the advertised data block. All timestamps will be a multiple of this quantum.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">blockEndTime</md:display-name>
                <md:description xml:lang="en">The time of the last data value in the advertised data block.</md:description>
            </field>
        </structure>
        <structure type-id="24">
            <md:code-name>DiscreteMetadata</md:code-name>
            <md:display-name xml:lang="en">Discrete Metadata</md:display-name>
            <md:description xml:lang="en">This structure defines metadata for data at a single point in time.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">deviceID</md:display-name>
                <md:description xml:lang="en">The ID of the device that originated the data.</md:description>
            </field>
            <field type-ref="32">
                <md:display-name xml:lang="en">parentID</md:display-name>
                <md:description xml:lang="en">The ID of the parent of the device, if applicable.  The parent is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
            <field type-ref="30">
                <md:display-name xml:lang="en">position</md:display-name>
                <md:description xml:lang="en">The position of the device relative to the parent, if applicable. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">time</md:display-name>
                <md:description xml:lang="en">The time associated with the advertised data.</md:description>
            </field>
        </structure>
        <structure type-id="27">
            <md:code-name>EventDefinition</md:code-name>
            <md:display-name xml:lang="en">Event Definition</md:display-name>
            <md:description xml:lang="en">[formatting] The definition of an event that will be exchanged.</md:description>
            <field type-ref="9">
                <md:display-name xml:lang="en">iid</md:display-name>
                <md:description xml:lang="en">The IID of the event.</md:description>
            </field>
            <field type-ref="25">
                <md:display-name xml:lang="en">itemID</md:display-name>
                <md:description xml:lang="en">The item ID of the event.</md:description>
            </field>
            <field type-ref="25">
                <md:display-name xml:lang="en">type</md:display-name>
                <md:description xml:lang="en">The item ID of an event in the reference interface that defines the event parameter types.</md:description>
            </field>
            <field type-ref="15">
                <md:display-name xml:lang="en">types</md:display-name>
                <md:description xml:lang="en">An interface definition used to store type definitions.</md:description>
            </field>
        </structure>
        <blob type-id="28" min-length="0" length="65535">
            <md:code-name>EventValues</md:code-name>
            <md:display-name xml:lang="en">Event Values</md:display-name>
            <md:description xml:lang="en">[formatting] A list of marshalled DOFValues matching the parameters of the event being signaled.</md:description>
        </blob>
        <structure type-id="29">
            <md:code-name>UncompressedEvent</md:code-name>
            <md:display-name xml:lang="en">Uncompressed Event</md:display-name>
            <md:description xml:lang="en">[formatting] A structure containing the entire event for transfer. Note that this type is never used, but it defines the uncompressed format of a block that is passed on the wire.</md:description>
            <field type-ref="27">
                <md:display-name xml:lang="en">event</md:display-name>
                <md:description xml:lang="en">An event definition.</md:description>
            </field>
            <field type-ref="28">
                <md:display-name xml:lang="en">values</md:display-name>
                <md:description xml:lang="en">The values associated with the event.</md:description>
            </field>
        </structure>
        <structure type-id="19">
            <md:code-name>ValueSetDefinitionStorage</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition Storage</md:display-name>
            <md:description xml:lang="en">[storage] This structure defines the peristence storage definition for the value set definition. Note that the format defined in this interface will always use an IID of [1:{0225}]. When reading the stored format the reader must check the version field and read the contents according to the correct IID.</md:description>
            <field type-ref="18">
                <md:display-name xml:lang="en">version</md:display-name>
                <md:description xml:lang="en">This field identifies the version of the stored data. All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            </field>
            <field type-ref="3">
                <md:display-name xml:lang="en">valueSetDefinition</md:display-name>
                <md:description xml:lang="en">The stored value set definition, assuming the version field is the IID of this interface.</md:description>
            </field>
        </structure>
        <blob type-id="33" min-length="0" length="1048576">
            <md:code-name>ValueSetBlock</md:code-name>
            <md:display-name xml:lang="en">Source Value Set Block</md:display-name>
            <md:description xml:lang="en">[storage] This value set is defined by the source IID and corresponds to the information transferred to the sink.</md:description>
        </blob>
        <structure type-id="8">
            <md:code-name>ValueSetStorage</md:code-name>
            <md:display-name xml:lang="en">Value Set Storage</md:display-name>
            <md:description xml:lang="en">[storage] This type describes the peristent storage format for data sets. All future versions of this interface must maintain the first field (version). Note that the format defined in this interface will always use an IID of [1:{0225}]. When reading the stored format the reader must check the version field and read the contents according to the correct IID.</md:description>
            <field type-ref="18">
                <md:display-name xml:lang="en">version</md:display-name>
                <md:description xml:lang="en">The version of the storage format, defined as the IID that defines the format. All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            </field>
            <field type-ref="23">
                <md:display-name xml:lang="en">metaData</md:display-name>
                <md:description xml:lang="en">The meta-data associated with the value set.</md:description>
            </field>
            <field type-ref="34">
                <md:display-name xml:lang="en">dataDefinition</md:display-name>
                <md:description xml:lang="en">The compressed representation of the data definition.</md:description>
            </field>
            <field type-ref="31">
                <md:display-name xml:lang="en">sourceVersion</md:display-name>
                <md:description xml:lang="en">The version of the source storage format, defined as the source IID that defines the format.</md:description>
            </field>
            <field type-ref="33">
                <md:display-name xml:lang="en">sourceData</md:display-name>
                <md:description xml:lang="en">The block associated with the source and identified by the source version. See the source IID for the definition of the contents of this block.</md:description>
            </field>
        </structure>
        <structure type-id="2">
            <md:code-name>EventStorage</md:code-name>
            <md:display-name xml:lang="en">Event Storage</md:display-name>
            <md:description xml:lang="en">[storage] This type describes the peristent storage format for an event. All future versions of this interface must maintain the first field (version). All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            <field type-ref="18">
                <md:display-name xml:lang="en">version</md:display-name>
                <md:description xml:lang="en">The version of the storage format, defined as the IID that defines the format. All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            </field>
            <field type-ref="24">
                <md:display-name xml:lang="en">metaData</md:display-name>
                <md:description xml:lang="en">The meta-data associated with the event.</md:description>
            </field>
            <field type-ref="21">
                <md:display-name xml:lang="en">event</md:display-name>
                <md:description xml:lang="en">The compressed representation of both the event definition and values.</md:description>
            </field>
        </structure>
        <structure type-id="6">
            <md:code-name>TopologyStorage</md:code-name>
            <md:display-name xml:lang="en">Topology Storage</md:display-name>
            <md:description xml:lang="en">[storage] This type describes the peristent storage format for a topology update. All future versions of this interface must maintain the first field (version). All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            <field type-ref="18">
                <md:display-name xml:lang="en">version</md:display-name>
                <md:description xml:lang="en">The version of the storage format, defined as the IID that defines the format. All versions of the storage will start with the same version type, and a generic reader can read the version and identify the format that follows. This definition uses the value [1:{0225}].</md:description>
            </field>
            <field type-ref="24">
                <md:display-name xml:lang="en">metaData</md:display-name>
                <md:description xml:lang="en">The meta-data associated with the topology update.</md:description>
            </field>
        </structure>
        <uint32 type-id="35">
            <md:code-name>DetailRequestID</md:code-name>
            <md:display-name xml:lang="en">Detail Request ID</md:display-name>
            <md:description xml:lang="en">An identifier provided by the sink to a request for detail data made to the source.</md:description>
        </uint32>
        <uint8 type-id="36">
            <md:code-name>DetailRequestStatus</md:code-name>
            <md:display-name xml:lang="en">Detail Request Status</md:display-name>
            <md:description xml:lang="en">A code indicating the completion status of the detail request. The following values are defined, other values are illegal and should be treated as errors. COMPLETE(0) - success;  COMPLETE_NO_DATA(1) - no data was found for the requested time and duration; FAILED(2) - general error; CANCELLED(3) - request cancelled;  TIMEOUT(4) -- The request timed out before completing.</md:description>
        </uint8>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>Advertise</md:code-name>
            <md:display-name xml:lang="en">Advertise Data</md:display-name>
            <md:description xml:lang="en">Advertise a value set that the sink may get and must acknowledge. This method may return ''Value Set Definition Not Understood'' if the particular data definition cannot be understood, which would prevent any data transfer for that definition.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:display-name xml:lang="en">sourceID</md:display-name>
                    <md:description xml:lang="en">The ID of the node responsible for transferring the value set.</md:description>
                </input>
                <input type-ref="1">
                    <md:display-name xml:lang="en">valueSetID</md:display-name>
                    <md:description xml:lang="en">An identifier associated with the advertised value set and the sink, that is used to uniquely identify the value set/sink combination.</md:description>
                </input>
                <input type-ref="23">
                    <md:display-name xml:lang="en">metaData</md:display-name>
                    <md:description xml:lang="en">The meta-data associated with the value set.</md:description>
                </input>
                <input type-ref="3">
                    <md:display-name xml:lang="en">valueSetDefinition</md:display-name>
                    <md:description xml:lang="en">The full value set definition associated with the advertised information.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="7">
                    <md:display-name xml:lang="en">valueSetDefinitionAlias</md:display-name>
                    <md:description xml:lang="en">The alias that the sink wants the source to use to refer to this particular value set definition.</md:description>
                </output>
            </outputs>
        </method>
        <method item-id="2">
            <md:code-name>AdvertiseWithAlias</md:code-name>
            <md:display-name xml:lang="en">Advertise Data With Alias</md:display-name>
            <md:description xml:lang="en">Advertise a value set using an alias that the sink may get and must acknowledge. This method may cause a Data Definition Alias Not Understood exception.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:display-name xml:lang="en">sourceID</md:display-name>
                    <md:description xml:lang="en">The ID of the node responsible for transferring the data.</md:description>
                </input>
                <input type-ref="1">
                    <md:display-name xml:lang="en">valueSetID</md:display-name>
                    <md:description xml:lang="en">An identifier associated with the advertised value set and sink, that is used to uniquely identify the value set/sink combination.</md:description>
                </input>
                <input type-ref="23">
                    <md:display-name xml:lang="en">metaData</md:display-name>
                    <md:description xml:lang="en">The meta-data associated with the value set.</md:description>
                </input>
                <input type-ref="7">
                    <md:display-name xml:lang="en">valueSetDefinitionAlias</md:display-name>
                    <md:description xml:lang="en">An alias that represents the value set definition that the sink has associated for the source.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="4">
            <md:code-name>Signal</md:code-name>
            <md:display-name xml:lang="en">Signal Event</md:display-name>
            <md:description xml:lang="en">Signal and event that the sink must acknowledge. This method does not cause any provider exceptions.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:display-name xml:lang="en">sourceID</md:display-name>
                    <md:description xml:lang="en">The ID of the node responsible for transferring the event.</md:description>
                </input>
                <input type-ref="20">
                    <md:display-name xml:lang="en">eventID</md:display-name>
                    <md:description xml:lang="en">An identifier associated with the signaled event and the sink, that is used to uniquely identify the event/sink combination.</md:description>
                </input>
                <input type-ref="24">
                    <md:display-name xml:lang="en">metaData</md:display-name>
                    <md:description xml:lang="en">The meta-data associated with the event.</md:description>
                </input>
                <input type-ref="21">
                    <md:display-name xml:lang="en">eventDefinition</md:display-name>
                    <md:description xml:lang="en">The event being signaled, including both definition and values.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="5">
            <md:code-name>UpdateTopology</md:code-name>
            <md:display-name xml:lang="en">Update Topology</md:display-name>
            <md:description xml:lang="en">Update the topology of a node. This method does not cause any provider exceptions.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:display-name xml:lang="en">sourceID</md:display-name>
                    <md:description xml:lang="en">The ID of the node responsible for transferring the update.</md:description>
                </input>
                <input type-ref="22">
                    <md:display-name xml:lang="en">topologyID</md:display-name>
                    <md:description xml:lang="en">An identifier associated with the update and the sink, that is used to uniquely identify the update/sink combination.</md:description>
                </input>
                <input type-ref="24">
                    <md:display-name xml:lang="en">metaData</md:display-name>
                    <md:description xml:lang="en">The meta-data associated with the update.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="6">
            <md:code-name>CompleteDetailRequest</md:code-name>
            <md:display-name xml:lang="en">Detail Request Complete</md:display-name>
            <md:description xml:lang="en">Notify the sink that a request for detail data has been completed.</md:description>
            <inputs>
                <input type-ref="35">
                    <md:display-name xml:lang="en">id</md:display-name>
                    <md:description xml:lang="en">The ID of the request for detail data.</md:description>
                </input>
                <input type-ref="36">
                    <md:display-name xml:lang="en">status</md:display-name>
                    <md:description xml:lang="en">The status of the request for detail data.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
    <exceptions>
        <exception item-id="3">
            <md:code-name>ValueSetDefinitionAliasNotUnderstood</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition Alias Not Understood</md:display-name>
            <md:description xml:lang="en">Returned by the sink if the value set definition alias received is not understood. This indicates that the advertise should be resent including the value set definition and a new alias obtained.</md:description>
        </exception>
        <exception item-id="7">
            <md:code-name>ValueSetDefinitionNotUnderstood</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition Not Understood</md:display-name>
            <md:description xml:lang="en">Returned by the sink if the value set definition received is not understood. This indicates some formatting issue on the source, or a programming error on the sink. Resubmitting the same definition will likely resultin the same exception, so this can be considered fatal for the definition in question and would prevent any data transfer.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:58','2015-09-21 18:06:58',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (30,'[1:{0228}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0228}]">
    <md:code-name>DATASNAPSHOT</md:code-name>
    <md:display-name xml:lang="en">Data Snapshot</md:display-name>
    <md:description xml:lang="en">
        This interface provides a snapshot of a known, public reference interface for a binding. This interface is typically activated on demand using an object id containing the Binding Provider attribute, which indicates the public reference interface for which this interface provides a snapshot. In this case, the definition will be the set of readable properties of the reference interface, in order of ascending item ID. Alternately, the referenced interface may merely define a constant set of properties (possibly from multiple, disparate interfaces) that comprise the snapshot. Finally, this interface may be provided on a session where the definition may be defined some other way (within the context of the session).
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000027">Data Snapshot Config</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <datetime type-id="0">
            <md:code-name>Timestamp</md:code-name>
            <md:display-name xml:lang="en">Timestamp</md:display-name>
            <md:description xml:lang="en">A timestamp.</md:description>
        </datetime>
        <uint16 type-id="1">
            <md:code-name>Property</md:code-name>
            <md:display-name xml:lang="en">Property</md:display-name>
            <md:description xml:lang="en">A property index, with zero being the first property.</md:description>
        </uint16>
        <uint16 type-id="2">
            <md:code-name>Count</md:code-name>
            <md:display-name xml:lang="en">Count</md:display-name>
            <md:description xml:lang="en">A count of data values used to produce a property value, which can be zero to indicate that the property is not present in the property values list.</md:description>
        </uint16>
        <structure type-id="3">
            <md:code-name>PropertyInfo</md:code-name>
            <md:display-name xml:lang="en">Property Information</md:display-name>
            <md:description xml:lang="en">A structure containing a property index and the count for that property. If the count is the default for the snapshot then no structure is necessary.</md:description>
            <field type-ref="1">
                <md:display-name xml:lang="en">property</md:display-name>
                <md:description xml:lang="en">The property index.</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">count</md:display-name>
                <md:description xml:lang="en">The count for the property.</md:description>
            </field>
        </structure>
        <array type-id="4" type-ref="3" min-length="0" length="32767">
            <md:code-name>PropertyInfoArray</md:code-name>
            <md:display-name xml:lang="en">Property Information Array</md:display-name>
            <md:description xml:lang="en">A variable array of Property Information structures indicating properties that have non-default counts. This can be used to ''skip'' properties (a zero count) or indicate that a non-default number of samples were used in the case of aggregate data.</md:description>
        </array>
        <blob type-id="5" min-length="0" length="1048576">
            <md:code-name>Values</md:code-name>
            <md:display-name xml:lang="en">Property Values</md:display-name>
            <md:description xml:lang="en">A block of EMIT-marshalled values corresponding to the snapshot values at a particular time. These are marshalled in the same order as the snapshot definition.</md:description>
        </blob>
        <structure type-id="6">
            <md:code-name>Snapshot</md:code-name>
            <md:display-name xml:lang="en">Snapshot</md:display-name>
            <md:description xml:lang="en">The information for a snapshot, including the timestamp and property data. This corresponds to a single Row type, but outside of the context of a Value Set, defined by the Data Source [{01}:{0226}] interface.</md:description>
            <field type-ref="0">
                <md:display-name xml:lang="en">timestamp</md:display-name>
                <md:description xml:lang="en">The timestamp of the snapshot.</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">defaultCount</md:display-name>
                <md:description xml:lang="en">The default count for the snapshot.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">propertyCounts</md:display-name>
                <md:description xml:lang="en">A list of exceptions to the default count value for properties in this snapshot.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">propertyValues</md:display-name>
                <md:description xml:lang="en">The values for the properties with non-zero counts.</md:description>
            </field>
        </structure>
        <blob type-id="7" min-length="0" length="65535">
            <md:code-name>Definition</md:code-name>
            <md:display-name xml:lang="en">Definition</md:display-name>
            <md:description xml:lang="en">A compressed block that defines the properties that are being transferred in the advertisement. The compression algorithm is DEFLATE (RFC 1951). The uncompressed block is an EMIT-marshalled instance of the ''Uncompressed Value Set Definition'' type defined in the Data Sink [{01}:{0225}] interface.</md:description>
        </blob>
    </typedefs>
    <properties>
        <property item-id="0" type-ref="7" read="true" write="false">
            <md:code-name>definition</md:code-name>
            <md:display-name xml:lang="en">Definition</md:display-name>
            <md:description xml:lang="en">The full definition of the data held in the Snapshot property of this interface.</md:description>
        </property>
        <property item-id="1" type-ref="6" read="true" write="false">
            <md:code-name>snapshot</md:code-name>
            <md:display-name xml:lang="en">Snapshot</md:display-name>
            <md:description xml:lang="en">The snapshot.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:58','2015-09-21 18:07:00',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (31,'[1:{01000027}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000027}]">
    <md:code-name>DATASNAPSHOTCONFIG</md:code-name>
    <md:display-name xml:lang="en">Data Snapshot Configuration</md:display-name>
    <md:description xml:lang="en">
        This interface provides a way to configure the contents of a Data Snapshot interface. This must only be provided on a session, where the session state provides the context and state between this configured definition and the Data Snapshot interface (also provided on the session, after this interface is used to configure the provided definition). This interface should no longer be provided on the session once the definition has been set (a new session should be established for a different definition).
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0228">Data Snapshot</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <blob type-id="7" min-length="0" length="65535">
            <md:code-name>Definition</md:code-name>
            <md:display-name xml:lang="en">Definition</md:display-name>
            <md:description xml:lang="en">A compressed block that defines the properties that are being transferred in the advertisement. The compression algorithm is DEFLATE (RFC 1951). The uncompressed block is an EMIT-marshalled instance of the ''Uncompressed Value Set Definition'' type defined in the Data Sink [{01}:{0225}] interface.</md:description>
        </blob>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>setDefinition</md:code-name>
            <md:display-name xml:lang="en">Set Definition</md:display-name>
            <md:description xml:lang="en">Request to provide the specified definition of data via the Data Snapshot interface on the current session.</md:description>
            <inputs>
                <input type-ref="7">
                    <md:display-name xml:lang="en">definition</md:display-name>
                    <md:description xml:lang="en">The definition.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
    <exceptions>
        <exception item-id="2">
            <md:code-name>notUnderstood</md:code-name>
            <md:display-name xml:lang="en">Definition Not Understood</md:display-name>
            <md:description xml:lang="en">Returned from the method if the definition received is not understood. This indicates some formatting issue on the requestor, or a programming error on the provider. Resubmitting the same definition will likely result in the same exception, so this can be considered fatal for the definition in question and would prevent any data transfer.</md:description>
        </exception>
        <exception item-id="3">
            <md:code-name>notAvailable</md:code-name>
            <md:display-name xml:lang="en">Not Available</md:display-name>
            <md:description xml:lang="en">Returned from the method if the definition received cannot be provided. This indicates the provider is not capable of providing the requested definition (e.g., unimplemented interfaces, properties, or aggregation types). Resubmitting the same definition will likely result in the same exception, so this can be considered fatal for the definition in question and would prevent any data transfer.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:59','2015-09-21 18:06:56',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (32,'[1:{0226}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0226}]">
    <md:code-name>DATASOURCE</md:code-name>
    <md:display-name xml:lang="en">Data Source</md:display-name>
    <md:description xml:lang="en">
        This interface is used by a sink to get and acknowledge value sets, events, and topology updates based on an advertisement from a data source. It is also used to request detail data from sources that provided aggregated data. There are unused types defined that correspond to the formatting data blocks before compression. In this case the compressed field is passed on the wire, and the unused type defines the format and contents of the block before compression. These types are identified by putting ''[formatting]'' in their descriptions.
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0225">Data Sink</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <uint32 type-id="1">
            <md:code-name>ValueSetID</md:code-name>
            <md:display-name xml:lang="en">Value Set ID</md:display-name>
            <md:description xml:lang="en">An identifier for the advertised value set, unique to the sink and source involved in the transfer. When used to either get or acknowledge the value set this identifier uniquely identifies the value set and the sink it is being transferred to.</md:description>
        </uint32>
        <uint32 type-id="2">
            <md:code-name>EventID</md:code-name>
            <md:display-name xml:lang="en">Event ID</md:display-name>
            <md:description xml:lang="en">An identifier for a signaled event, unique to the sink and source involved in the transfer. When used to acknowledge the event this identifier uniquely identifies the event and the sink the event is being transferred to.</md:description>
        </uint32>
        <uint32 type-id="3">
            <md:code-name>TopologyID</md:code-name>
            <md:display-name xml:lang="en">Topology ID</md:display-name>
            <md:description xml:lang="en">An identifier for a topology update, unique to the sink and source involved in the transfer. When used to acknowledge the update this identifier uniquely identifies the update and the sink the event is being transferred to.</md:description>
        </uint32>
        <uint32 type-id="15">
            <md:code-name>DetailRequestID</md:code-name>
            <md:display-name xml:lang="en">Detail Request ID</md:display-name>
            <md:description xml:lang="en">An identifier for a detail request made by the sink, unique to the sink.</md:description>
        </uint32>
        <blob type-id="4" min-length="0" length="1048576">
            <md:code-name>ValueSet</md:code-name>
            <md:display-name xml:lang="en">Value Set</md:display-name>
            <md:description xml:lang="en">A compressed block that contains property values and timestamps that are being transferred to the sink. The compression algorithm is DEFLATE (RFC 1951). The uncompressed block is an EMIT-marshalled instance of the ''Uncompressed Value Set'' type defined in this interface.</md:description>
        </blob>
        <blob type-id="5" min-length="0" length="1048576">
            <md:code-name>PropertyValues</md:code-name>
            <md:display-name xml:lang="en">Property Values</md:display-name>
            <md:description xml:lang="en">[formatting] A block of EMIT-marshalled values corresponding to the value set definition values at a particular time. These are marshalled in the same order as the value set definition.</md:description>
        </blob>
        <uint16 type-id="6">
            <md:code-name>Column</md:code-name>
            <md:display-name xml:lang="en">Column</md:display-name>
            <md:description xml:lang="en">[formatting] A column index, with zero being the first column.</md:description>
        </uint16>
        <uint16 type-id="7">
            <md:code-name>Count</md:code-name>
            <md:display-name xml:lang="en">Count</md:display-name>
            <md:description xml:lang="en">[formatting] A count of data values used to produce a column value, which can be zero to indicate that the column is not present in the property values list.</md:description>
        </uint16>
        <structure type-id="8">
            <md:code-name>ColumnCount</md:code-name>
            <md:display-name xml:lang="en">Column Information</md:display-name>
            <md:description xml:lang="en">[formatting] A structure containing a column and the count for that column. If the count is the default for the row then no structure is necessary.</md:description>
            <field type-ref="6">
                <md:display-name xml:lang="en">column</md:display-name>
                <md:description xml:lang="en">The column index.</md:description>
            </field>
            <field type-ref="7">
                <md:display-name xml:lang="en">count</md:display-name>
                <md:description xml:lang="en">The count for the column.</md:description>
            </field>
        </structure>
        <array type-id="9" type-ref="8" min-length="0" length="32767">
            <md:code-name>ColumnCountArray</md:code-name>
            <md:display-name xml:lang="en">Column Information Array</md:display-name>
            <md:description xml:lang="en">[formatting] A variable array of Column Count structures indicating columns that have non-default counts. This can be used to ''skip'' columns (a zero count) or indicate that a non-default number of samples were used in the case of aggregate data.</md:description>
        </array>
        <uint8 type-id="12">
            <md:code-name>DeltaTime</md:code-name>
            <md:display-name xml:lang="en">Delta Time</md:display-name>
            <md:description xml:lang="en">[formatting] The timestamp of a row, expressed as the number of time quanta from the timestamp of the previous row.</md:description>
        </uint8>
        <structure type-id="13">
            <md:code-name>Row</md:code-name>
            <md:display-name xml:lang="en">Row</md:display-name>
            <md:description xml:lang="en">[formatting] The information for a row, including the timestamp and column data.</md:description>
            <field type-ref="12">
                <md:display-name xml:lang="en">delta</md:display-name>
                <md:description xml:lang="en">The timestamp, represented as a delta of quanta from the previous row.</md:description>
            </field>
            <field type-ref="7">
                <md:display-name xml:lang="en">defaultCount</md:display-name>
                <md:description xml:lang="en">The default count for the row.</md:description>
            </field>
            <field type-ref="9">
                <md:display-name xml:lang="en">columnCounts</md:display-name>
                <md:description xml:lang="en">A list of exceptions to the default count value for columns in this row.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">propertyValues</md:display-name>
                <md:description xml:lang="en">The values for the properties with non-zero counts.</md:description>
            </field>
        </structure>
        <array type-id="14" type-ref="13" min-length="1" length="32767">
            <md:code-name>UncompressedValueSet</md:code-name>
            <md:display-name xml:lang="en">Uncompressed Value Set</md:display-name>
            <md:description xml:lang="en">[formatting] An array of rows.</md:description>
        </array>
        <oid type-id="16">
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID</md:display-name>
            <md:description xml:lang="en">An identifier for an actor in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <iid type-id="17">
            <md:code-name>PropertyIID</md:code-name>
            <md:display-name xml:lang="en">Property IID</md:display-name>
            <md:description xml:lang="en">The identifier for a property interface.</md:description>
        </iid>
        <uint16 type-id="18" min="0" max="32767">
            <md:code-name>PropertyItemID</md:code-name>
            <md:display-name xml:lang="en">Property Item ID</md:display-name>
            <md:description xml:lang="en">A property item ID.</md:description>
        </uint16>
        <datetime type-id="19">
            <md:code-name>Timestamp</md:code-name>
            <md:display-name xml:lang="en">Timestamp</md:display-name>
            <md:description xml:lang="en">A timestamp.</md:description>
        </datetime>
        <uint32 type-id="20">
            <md:code-name>Milliseconds</md:code-name>
            <md:display-name xml:lang="en">Milliseconds</md:display-name>
            <md:description xml:lang="en">A time range in milliseconds.</md:description>
        </uint32>
        <structure type-id="21">
            <md:code-name>Request</md:code-name>
            <md:display-name xml:lang="en">Request</md:display-name>
            <md:description xml:lang="en">A request for detail data. This information may be sent to multiple sources without modification in cases where sources contain partial information for the time range desired.</md:description>
            <field type-ref="16">
                <md:display-name xml:lang="en">device</md:display-name>
                <md:description xml:lang="en">The device that provided the aggregate data and that detail data should be provided for.</md:description>
            </field>
            <field type-ref="17">
                <md:display-name xml:lang="en">propertyIID</md:display-name>
                <md:description xml:lang="en">The IID of the property to return detail data about.</md:description>
            </field>
            <field type-ref="18">
                <md:display-name xml:lang="en">propertyItemID</md:display-name>
                <md:description xml:lang="en">The Item ID of the property to return detail data about.</md:description>
            </field>
            <field type-ref="19">
                <md:display-name xml:lang="en">startTime</md:display-name>
                <md:description xml:lang="en">The start time of the detail data to return.</md:description>
            </field>
            <field type-ref="20">
                <md:display-name xml:lang="en">timeRange</md:display-name>
                <md:description xml:lang="en">The time range of the detail data to return.</md:description>
            </field>
        </structure>
        <uint32 type-id="10">
            <md:code-name>QueuedValueSetCount</md:code-name>
            <md:display-name xml:lang="en">Queued Value Set Count</md:display-name>
            <md:description xml:lang="en">The number of Value Sets that are queued for delivery to the sink.</md:description>
        </uint32>
        <uint16 type-id="22">
            <md:code-name>Hours</md:code-name>
            <md:display-name xml:lang="en">Hours</md:display-name>
            <md:description xml:lang="en">A time range in hours.</md:description>
        </uint16>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>Get</md:code-name>
            <md:display-name xml:lang="en">Get</md:display-name>
            <md:description xml:lang="en">Retrieve a value set from a source. This may cause an Unknown Value Set Identifier exception.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">valueSetID</md:display-name>
                    <md:description xml:lang="en">The value set identifier to return.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="4">
                    <md:display-name xml:lang="en">valueSet</md:display-name>
                    <md:description xml:lang="en">The value set.</md:description>
                </output>
            </outputs>
        </method>
        <method item-id="2">
            <md:code-name>AcknowledgeValueSet</md:code-name>
            <md:display-name xml:lang="en">Acknowledge Value Set</md:display-name>
            <md:description xml:lang="en">Acknowledge the transfer of a value set from a source. This does not return an exception even if the identifier is not found.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">valueSetID</md:display-name>
                    <md:description xml:lang="en">The value set identifier to acknowledge.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="10">
                    <md:display-name xml:lang="en">queuedValueSetCount</md:display-name>
                    <md:description xml:lang="en">The number of value sets still queued for the sink. Note that the sink is understood through the Value Set ID of the method.</md:description>
                </output>
            </outputs>
        </method>
        <method item-id="4">
            <md:code-name>AcknowledgeEvent</md:code-name>
            <md:display-name xml:lang="en">Acknowledge Event</md:display-name>
            <md:description xml:lang="en">Acknowledge the transfer of an event from a source. This does not return an exception even if the identifier is not found.</md:description>
            <inputs>
                <input type-ref="2">
                    <md:display-name xml:lang="en">eventID</md:display-name>
                    <md:description xml:lang="en">The event identifier to acknowledge.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="5">
            <md:code-name>AcknowledgeTopologyUpdate</md:code-name>
            <md:display-name xml:lang="en">Acknowledge Topology Update</md:display-name>
            <md:description xml:lang="en">Acknowledge the transfer of a topology update from a source. This does not return an exception even if the identifier is not found.</md:description>
            <inputs>
                <input type-ref="3">
                    <md:display-name xml:lang="en">updateID</md:display-name>
                    <md:description xml:lang="en">The topology update identifier to acknowledge.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="6">
            <md:code-name>RequestDetail</md:code-name>
            <md:display-name xml:lang="en">Request Detail</md:display-name>
            <md:description xml:lang="en">Initiate a request for detail data. All data for the request and notification of its completion will be sent to the indicated sink. This may throw the Out of Range, Unknown Data, Unknown Sink, Out of Resource, or Duplicate RequestID exceptions.</md:description>
            <inputs>
                <input type-ref="15">
                    <md:display-name xml:lang="en">requestID</md:display-name>
                    <md:description xml:lang="en">An identifier to associate with this request. The combination of this ID and the sink should be unique.</md:description>
                </input>
                <input type-ref="16">
                    <md:display-name xml:lang="en">sink</md:display-name>
                    <md:description xml:lang="en">The target sink for the data. This sink follows the distribution rules for sinks, meaning that the request will be load-balanced based on the presence of provider attributes.</md:description>
                </input>
                <input type-ref="21">
                    <md:display-name xml:lang="en">request</md:display-name>
                    <md:description xml:lang="en">A structure containing information about the request.</md:description>
                </input>
                <input type-ref="20">
                    <md:display-name xml:lang="en">timeout</md:display-name>
                    <md:description xml:lang="en">The max duration that the sink is willing to wait for a response.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="7">
            <md:code-name>CancelRequestDetail</md:code-name>
            <md:display-name xml:lang="en">Cancel Request Detail</md:display-name>
            <md:description xml:lang="en">Cancel a request for detail data by a sink. The requesting sink and request ID are provided. This does not return an exception even if the request ID is not found.</md:description>
            <inputs>
                <input type-ref="15">
                    <md:display-name xml:lang="en">requestID</md:display-name>
                    <md:description xml:lang="en">The identifier associated with this request. The combination of this ID and the sink should be unique.</md:description>
                </input>
                <input type-ref="16">
                    <md:display-name xml:lang="en">sink</md:display-name>
                    <md:description xml:lang="en">The sink that requested the data.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
    <exceptions>
        <exception item-id="3">
            <md:code-name>UnknownValueSetIdentifier</md:code-name>
            <md:display-name xml:lang="en">Unknown Value Set Identifier</md:display-name>
            <md:description xml:lang="en">Returned by the source if the value set identifier is not recognized.</md:description>
        </exception>
        <exception item-id="8">
            <md:code-name>OuteOfRange</md:code-name>
            <md:display-name xml:lang="en">Out of Range</md:display-name>
            <md:description xml:lang="en">Returned by the source if a request for detailed data is outside the time range for which the source stores data or if the specified request timeout is greater than the source allows. Includes the max allowed request duration, timeout and historical age.</md:description>
            <outputs>
                <output type-ref="22">
                    <md:display-name xml:lang="en">Max Historical Age</md:display-name>
                    <md:description xml:lang="en">The maximum time, in hours, that the source will store detailed data.</md:description>
                </output>
                <output type-ref="20">
                    <md:display-name xml:lang="en">Max Duration</md:display-name>
                    <md:description xml:lang="en">The maximum request duration allowed by the source.</md:description>
                </output>
                <output type-ref="20">
                    <md:display-name xml:lang="en">Max Timeout</md:display-name>
                    <md:description xml:lang="en">The maximum timeout allowed by the source.</md:description>
                </output>
            </outputs>
        </exception>
        <exception item-id="9">
            <md:code-name>UnknownData</md:code-name>
            <md:display-name xml:lang="en">Unknown Data</md:display-name>
            <md:description xml:lang="en">Returned by the source if a request for detailed data is made for an unknown device or property. Requests should only be made to the source from which aggregate data was received and only during a time during which aggregate data was observed.</md:description>
        </exception>
        <exception item-id="10">
            <md:code-name>UnknownSink</md:code-name>
            <md:display-name xml:lang="en">Unknown Sink</md:display-name>
            <md:description xml:lang="en">Returned if the sink specified by a request is not known by the source. The request should be retried when the sink is present.</md:description>
        </exception>
        <exception item-id="11">
            <md:code-name>OutOfResource</md:code-name>
            <md:display-name xml:lang="en">Out of Resource</md:display-name>
            <md:description xml:lang="en">Returned by the source if it has insufficient resources to persist or process the request. The request may succeed later if additional resources become available.</md:description>
        </exception>
        <exception item-id="12">
            <md:code-name>DuplicateRequestID</md:code-name>
            <md:display-name xml:lang="en">Duplicate RequestID</md:display-name>
            <md:description xml:lang="en">Returned by the source if it already has an outstanding request with the specified ID.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:05:59','2015-09-21 18:06:58',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (33,'[1:{0100004D}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0100004D}]">
    <md:code-name>DATASTOREREQUEST</md:code-name>
    <md:display-name xml:lang="en">Data Store Request</md:display-name>
    <md:description xml:lang="en">This interface is used by a sink to request data from a Data Store. Once a request has been accepted, data is delivered to the Sink via a Source.</md:description>
    <typedefs>
        <uint32 type-id="1">
            <md:code-name>RequestID</md:code-name>
            <md:display-name xml:lang="en">Request ID</md:display-name>
            <md:description xml:lang="en">An identifier for a data request, unique to the target sink.</md:description>
        </uint32>
        <oid type-id="2">
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID</md:display-name>
            <md:description xml:lang="en">An identifier for an actor in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <iid type-id="3">
            <md:code-name>SinkVersion</md:code-name>
            <md:display-name xml:lang="en">Sink Version</md:display-name>
            <md:description xml:lang="en">An InterfaceID used to identify the version of information related to the sink InterfaceID associated with this Data Store Source InterfaceID. 
    Future versions of this interface will need to differentiate between versions. All data defined by this interface will begin with this type, allowing it to be read to identify the 
    data format that follows.</md:description>
        </iid>
        <blob type-id="4" min-length="0" length="1048576">
            <md:code-name>DefinitionBlock</md:code-name>
            <md:display-name xml:lang="en">Definition Block</md:display-name>
            <md:description xml:lang="en">A compressed block that defines the properties that are being requested. The format of this type is specified by Sink Version and defined by the
    corresponding interface.</md:description>
        </blob>
        <datetime type-id="5">
            <md:code-name>Timestamp</md:code-name>
            <md:display-name xml:lang="en">Timestamp</md:display-name>
            <md:description xml:lang="en">A timestamp.</md:description>
        </datetime>
        <uint32 type-id="6">
            <md:code-name>Milliseconds</md:code-name>
            <md:display-name xml:lang="en">Milliseconds</md:display-name>
            <md:description xml:lang="en">A time range in milliseconds.</md:description>
        </uint32>
        <structure type-id="7">
            <md:code-name>Request</md:code-name>
            <md:display-name xml:lang="en">Request</md:display-name>
            <md:description xml:lang="en">A request for any data with the specified device identifier, time quantum and any properties included in the definition.</md:description>
            <field type-ref="2">
                <md:display-name xml:lang="en">device</md:display-name>
                <md:description xml:lang="en">The device associated with the data.</md:description>
            </field>
            <field type-ref="3">
                <md:display-name xml:lang="en">Sink Version</md:display-name>
                <md:description xml:lang="en">The version of the sink storage format, defined as the sink IID that defines the format.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">Definition Block</md:display-name>
                <md:description xml:lang="en">A compressed block that defines the properties that are being requested. The format is defined by the Sink Version IID.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">startTime</md:display-name>
                <md:description xml:lang="en">The inclusive start time of the data to return.</md:description>
            </field>
            <field type-ref="5">
                <md:display-name xml:lang="en">endTime</md:display-name>
                <md:description xml:lang="en">The exclusive end time of the data to return. Must specify a time after the start time.</md:description>
            </field>
            <field type-ref="6">
                <md:display-name xml:lang="en">quantum</md:display-name>
                <md:description xml:lang="en">The time interval between stored data points for the requested properties. The requested quantum must exactly match the quantum of the stored data.</md:description>
            </field>
        </structure>
        <uint16 type-id="8" min="1" max="1440">
            <md:code-name>Minutes</md:code-name>
            <md:display-name xml:lang="en">Minutes</md:display-name>
            <md:description xml:lang="en">A time range in minutes, not to exceed one day.</md:description>
        </uint16>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>RequestData</md:code-name>
            <md:display-name xml:lang="en">Request Data</md:display-name>
            <md:description xml:lang="en">Initiate a request for data. All data for the request and notification of its completion will be sent to the indicated sink. 
    If the source is unable retrieve or send requested data for a period of time exceeding maxFailureTime, it will complete the request as Failed and attempt to notify the sink. Likewise,
    if the target sink does not receive any valuesets containing requested data, or notification of completion, for a period of time exceeding maxFailureTime,
    it may assume that the request has failed. There is no maximum amount of time that the request may take to complete as long as the source continues to be able
    to retrieve and send the requested data. This method may throw the No Data, Unknown Sink, Unknown Version, Insufficient Resources, Duplicate RequestID, Out of Range, or Value Set Definition Not Understood exceptions.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">requestID</md:display-name>
                    <md:description xml:lang="en">An identifier to associate with this request. The combination of this ID and the sink should be unique.</md:description>
                </input>
                <input type-ref="2">
                    <md:display-name xml:lang="en">sink</md:display-name>
                    <md:description xml:lang="en">The target sink for the data. This sink follows the distribution rules for sinks, meaning that the request will be load-balanced based on the presence of provider attributes.</md:description>
                </input>
                <input type-ref="7">
                    <md:display-name xml:lang="en">request</md:display-name>
                    <md:description xml:lang="en">A structure containing information about the request.</md:description>
                </input>
                <input type-ref="8">
                    <md:display-name xml:lang="en">maxFailureTime</md:display-name>
                    <md:description xml:lang="en">The maximum amount of time that the request may be in an error state, usually due to the inability to either retrieve the requested data or send it to the specified sink.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="2">
            <md:code-name>CancelRequestData</md:code-name>
            <md:display-name xml:lang="en">Cancel Request Data</md:display-name>
            <md:description xml:lang="en">Cancel a request for data. The target sink and request ID are provided. This method will return successfully whether or not the request ID is not found, but may throw the Insufficient Resources Exception.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">requestID</md:display-name>
                    <md:description xml:lang="en">The identifier associated with this request. The combination of this ID and the sink should be unique.</md:description>
                </input>
                <input type-ref="2">
                    <md:display-name xml:lang="en">sink</md:display-name>
                    <md:description xml:lang="en">The target sink for the requested data.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
    <exceptions>
        <exception item-id="3">
            <md:code-name>NoData</md:code-name>
            <md:display-name xml:lang="en">No Data</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if it can quickly determine that no data exists for the specified request parameters. If the absence of
    data cannot be quickly determined then a COMPLETE_NO_DATA request status may instead be sent to the target sink.</md:description>
        </exception>
        <exception item-id="4">
            <md:code-name>UnknownSink</md:code-name>
            <md:display-name xml:lang="en">Unknown Sink</md:display-name>
            <md:description xml:lang="en">Returned if the sink specified by a request is not known. The request should be retried when the sink is present.</md:description>
        </exception>
        <exception item-id="5">
            <md:code-name>UnknownVersion</md:code-name>
            <md:display-name xml:lang="en">Unknown Version</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if it does not support the sink version specified in a request.</md:description>
        </exception>
        <exception item-id="6">
            <md:code-name>InsufficientResources</md:code-name>
            <md:display-name xml:lang="en">Insufficient Resources</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if it has insufficient resources to persist or process the request. The request may succeed later if additional resources become available.</md:description>
        </exception>
        <exception item-id="7">
            <md:code-name>DuplicateRequestID</md:code-name>
            <md:display-name xml:lang="en">Duplicate RequestID</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if it already has an outstanding request with the specified ID for the target sink.</md:description>
        </exception>
        <exception item-id="8">
            <md:code-name>ValueSetDefinitionNotUnderstood</md:code-name>
            <md:display-name xml:lang="en">Value Set Definition Not Understood</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if the value set definition block is malformed or not understood.</md:description>
        </exception>
        <exception item-id="9">
            <md:code-name>OutOfRange</md:code-name>
            <md:display-name xml:lang="en">Out of Range</md:display-name>
            <md:description xml:lang="en">Returned by the datastore if the maxFailureTime is less than 1 or greater than 1440, or if the end time is not after the start time.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:00','2015-09-21 18:06:00',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (34,'[1:{09}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{09}]">
    <md:display-name xml:lang="en">Device Control</md:display-name>
    <md:description xml:lang="en">This interface represents control over a device that can be powered off and rebooted. All methods will return once the request is accepted and before the request has completed. ''Request Failed'' may be returned as an exception from all of the methods.</md:description>
    <typedefs>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:display-name xml:lang="en">Shutdown</md:display-name>
            <md:description xml:lang="en">Request that the device shutdown (power off). Network connectivity will be lost until some other action restarts the device, although a successful response to this method should be received.</md:description>
        </method>
        <method item-id="2">
            <md:display-name xml:lang="en">Reboot</md:display-name>
            <md:description xml:lang="en">Request that the device reboot (reset hardware and start executing cleanly). Network connectivity will be lost temporarily, although a successful response to this method should be received.</md:description>
        </method>
    </methods>
    <exceptions>
        <exception item-id="3">
            <md:display-name xml:lang="en">Request Failed</md:display-name>
            <md:description xml:lang="en">Returned from any of the methods if the request failed. This does not indicate a permanent failure or the reason for the failure, which may vary by device. Retrying the request at a later time is appropriate. There is no indication of how long is appropriate to wait before attempting the operation again.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:00','2015-09-21 18:07:01',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (35,'[1:{01}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01}]">
    <md:code-name>DEVICE_STATUS_INTERFACE</md:code-name>
    <md:display-name xml:lang="ja">Statusï¼ˆã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ï¼‰</md:display-name>
    <md:display-name xml:lang="en">Status</md:display-name>
    <md:description xml:lang="ja">ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã¯æ©Ÿå™¨ã«ã‚ˆã£ã¦æä¾›ã•ã‚Œã€ã“ã‚Œã«ã‚ˆã£ã¦æ©Ÿå™¨ã®ã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ã‚’ç¢ºèªã§ãã‚‹ã€‚</md:description>
    <md:description xml:lang="en">This interface is provided by devices and allows a device''s status to be retrieved.</md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Statusï¼ˆã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ï¼‰</md:display-name>
            <md:display-name xml:lang="en">Status</md:display-name>
            <md:description xml:lang="ja">ã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ã®åˆ—æŒ™åž‹å€¤ï¼šã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ã®å€¤ã¯ã€OKï¼ˆ0ï¼‰ã€WARNï¼ˆ1ï¼‰ã€ERRORï¼ˆ2ï¼‰ã®3ã¤ãŒã‚ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>Status</md:code-name>
            <md:display-name xml:lang="ja">Statusï¼ˆã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹ï¼‰</md:display-name>
            <md:display-name xml:lang="en">Status</md:display-name>
            <md:description xml:lang="ja">æ©Ÿå™¨ã®ã‚¹ãƒ†ãƒ¼ã‚¿ã‚¹</md:description>
            <md:description xml:lang="en">The status of the device.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:01','2015-09-21 18:06:57',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (36,'[1:{01000002}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000002}]">
    <md:code-name>IEVENT</md:code-name>
    <md:display-name xml:lang="ja">ãƒˆãƒ¬ãƒ¼ãƒ‹ãƒ³ã‚°ç”¨ã‚¤ãƒ™ãƒ³ãƒˆ</md:display-name>
    <md:display-name xml:lang="en">Training Event</md:display-name>
    <md:description xml:lang="ja">
        ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã¯ã€ãƒ¡ã‚½ãƒƒãƒ‰ã¨ã‚¤ãƒ™ãƒ³ãƒˆã‚’éžå¸¸ã«ç°¡å˜ã«å®Ÿè£…ã™ã‚‹ãŸã‚ã®æ‰‹æ®µã‚’æä¾›ã™ã‚‹ã€‚ãƒˆãƒ¬ãƒ¼ãƒŠãƒ¼ç”¨ãƒ™ãƒ¼ã‚¹ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ã‚¤ã‚¹ã¨ä½µç”¨ã™ã‚‹å ´åˆã¯ã€ã‚»ãƒƒã‚·ãƒ§ãƒ³ã¨ã—ã¦æä¾›ã™ã‚‹ã“ã¨ãŒå¯èƒ½ã§ã‚ã‚‹ã€‚
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000000">Training Base</a></li>
   	    </ul>]]>
    </md:description>
    <md:description xml:lang="en">
        This interface provides an easy-to-implement method and event. It can be provided as a session if used in conjunction with the Training Base Interface.
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000000">Training Base</a></li>
   	    </ul>]]>
    </md:description>
    <typedefs>
        <uint8 type-id="0">
            <md:code-name>Seconds</md:code-name>
            <md:display-name xml:lang="ja">Seconds</md:display-name>
            <md:display-name xml:lang="en">Seconds</md:display-name>
            <md:description xml:lang="ja">ç§’å˜ä½ã®æ™‚é–“ã€‚</md:description>
            <md:description xml:lang="en">A quantity of time in seconds.</md:description>
        </uint8>
        <string type-id="1" encoding="3" length="32">
            <md:code-name>Message</md:code-name>
            <md:display-name xml:lang="ja">Message</md:display-name>
            <md:display-name xml:lang="en">Message</md:display-name>
            <md:description xml:lang="ja">çŸ­ã„ASCIIæ–‡å­—åˆ—ã€‚</md:description>
            <md:description xml:lang="en">A short ASCII string.</md:description>
        </string>
    </typedefs>
    <properties>
        <property item-id="2" type-ref="0" read="true" write="false">
            <md:code-name>Countdown</md:code-name>
            <md:display-name xml:lang="ja">Countdown</md:display-name>
            <md:display-name xml:lang="en">Countdown</md:display-name>
            <md:description xml:lang="ja">ã“ã®èª­ã¿å–ã‚Šå°‚ç”¨ãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã¯ã€ã‚¼ãƒ­ã¾ã§ã‚«ã‚¦ãƒ³ãƒˆãƒ€ã‚¦ãƒ³ã™ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">This read-only property counts down to zero.</md:description>
        </property>
        <property item-id="3" type-ref="1" read="true" write="true">
            <md:code-name>EventText</md:code-name>
            <md:display-name xml:lang="ja">EventText</md:display-name>
            <md:display-name xml:lang="en">EventText</md:display-name>
            <md:description xml:lang="ja">ã‚¤ãƒ™ãƒ³ãƒˆãŒç™ºç”Ÿã—ãŸéš›ã«é€ä¿¡ã•ã‚Œã‚‹ãƒ†ã‚­ã‚¹ãƒˆæ–‡å­—åˆ—ã€‚ç©ºã«ã™ã‚‹ã“ã¨ã¯ã§ããªã„ã€‚ã“ã®ãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã«ç©ºã®æ–‡å­—åˆ—ã‚’ã‚»ãƒƒãƒˆã—ã‚ˆã†ã¨ã™ã‚‹ã¨ã€ä¾‹å¤–ã€ŒEventTextInvalidã€ãŒå‡ºåŠ›ã•ã‚Œã‚‹ã€‚</md:description>
            <md:description xml:lang="en">This is the text string that is sent when an event occurs. It must not be empty. The EventTextInvalid exception is thrown if you attempt to set this property to an empty string.</md:description>
        </property>
    </properties>
    <methods>
        <method item-id="1">
            <md:code-name>ScheduleEvent</md:code-name>
            <md:display-name xml:lang="ja">ScheduleEvent</md:display-name>
            <md:display-name xml:lang="en">ScheduleEvent</md:display-name>
            <md:description xml:lang="ja">ã‚¤ãƒ™ãƒ³ãƒˆã‚’ã‚¹ã‚±ã‚¸ãƒ¥ãƒ¼ãƒ«ã™ã‚‹ã€‚ã“ã®ãƒ—ãƒ­ãƒã‚¤ãƒ€ä¸Šã§ç¾åœ¨ã‚¹ã‚±ã‚¸ãƒ¥ãƒ¼ãƒ«ã•ã‚Œã¦ã„ã‚‹ã™ã¹ã¦ã®ã‚¤ãƒ™ãƒ³ãƒˆãŒç½®ãæ›ãˆã‚‰ã‚Œã‚‹ã€‚EventTextãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ãŒç©ºã®æ–‡å­—åˆ—ã§ã‚ã‚‹å ´åˆã€ã“ã®ãƒ¡ã‚½ãƒƒãƒ‰ã¯ä¾‹å¤–ã€ŒEventTextInvalidã€ã‚’å‡ºåŠ›ã™ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">This schedules an event. Any currently scheduled event on this provider is replaced. If the EventText property is an empty string, this method throws the EventTextInvalid exception.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:display-name xml:lang="ja">delay</md:display-name>
                    <md:display-name xml:lang="en">delay</md:display-name>
                    <md:description xml:lang="ja">ã‚¤ãƒ™ãƒ³ãƒˆãŒãƒˆãƒªã‚¬ã•ã‚Œã‚‹ã¾ã§ã®ç§’æ•°ã€‚ãƒ¡ã‚½ãƒƒãƒ‰ã«ã‚ˆã£ã¦Countdownãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ãŒã“ã®å€¤ã«ã‚»ãƒƒãƒˆã•ã‚Œã‚‹ã€‚</md:description>
                    <md:description xml:lang="en">The number of seconds that should elapse before the event is triggered. The method sets the Countdown property to this value.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
    <events>
        <event item-id="4">
            <md:code-name>StringEvent</md:code-name>
            <md:display-name xml:lang="ja">StringEvent</md:display-name>
            <md:display-name xml:lang="en">StringEvent</md:display-name>
            <md:description xml:lang="ja">ã“ã®ã‚¤ãƒ™ãƒ³ãƒˆãŒç™ºç”Ÿã—ãŸå ´åˆã¯ã€EventTextãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ãŒãƒªã‚¯ã‚¨ã‚¹ã‚¿ã«é€ä¿¡ã•ã‚Œã‚‹ã€‚</md:description>
            <md:description xml:lang="en">When this event occurs, the EventText property is sent to the requestor.</md:description>
            <outputs>
                <output type-ref="1">
                    <md:display-name xml:lang="ja">str</md:display-name>
                    <md:display-name xml:lang="en">str</md:display-name>
                    <md:description xml:lang="ja">EventTextãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã®ç¾åœ¨ã®å€¤ã€‚</md:description>
                    <md:description xml:lang="en">The current value of the EventText property.</md:description>
                </output>
            </outputs>
        </event>
    </events>
    <exceptions>
        <exception item-id="5">
            <md:code-name>EventTextInvalid</md:code-name>
            <md:display-name xml:lang="ja">EventTextInvalid</md:display-name>
            <md:display-name xml:lang="en">EventTextInvalid</md:display-name>
            <md:description xml:lang="ja">EventTextãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ãŒç©ºã®æ–‡å­—åˆ—ã§ã‚ã‚‹å ´åˆã€ã“ã®ä¾‹å¤–ãŒå‡ºåŠ›ã•ã‚Œã‚‹ã€‚</md:description>
            <md:description xml:lang="en">This exception is thrown if the EventText property is an empty string.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:02','2015-09-21 18:06:02',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (37,'[1:{0A}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0A}]">
    <md:display-name xml:lang="ja">ãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡è¨±å¯</md:display-name>
    <md:display-name xml:lang="en">Local Control Enable</md:display-name>
    <md:description xml:lang="ja">ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã¯ã€æ©Ÿå™¨ã«å¯¾ã™ã‚‹ãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã‚’è¨±å¯ã™ã‚‹ã‹ä¸è¨±å¯ã«ã™ã‚‹ã‹ã®è¨­å®šæ©Ÿèƒ½ã‚’æä¾›ã—ã¾ã™ã€‚ã€Œãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã€ã¨ã¯ã€é€šä¿¡ã‚»ã‚­ãƒ¥ãƒªãƒ†ã‚£ãƒ¢ãƒ‡ãƒ«ã®ç¯„å›²ã§åˆ¶å¾¡ã‚’åˆ¶é™ã™ã‚‹ã“ã¨ãŒã§ããªã„ã‚ˆã†ãªæ‰‹æ®µã«ã‚ˆã‚‹åˆ¶å¾¡å…¨èˆ¬ã‚’æŒ‡ã—ã¾ã™ã€‚ä¾‹ãˆã°ãã®æ©Ÿå™¨ãŒæŒã¤ç‰©ç†çš„ãªãƒœã‚¿ãƒ³ã‚„ã€ãã®æ©Ÿå™¨ã‚’åˆ¶å¾¡ã™ã‚‹ãŸã‚ã®èµ¤å¤–ç·šãƒªãƒ¢ã‚³ãƒ³ã‚„ã‚³ãƒ³ãƒˆãƒ­ãƒ¼ãƒ«ãƒ‘ãƒãƒ«ã€ãªã©ã«ã‚ˆã‚‹åˆ¶å¾¡ã‚’æŒ‡ã—ã¾ã™ã€‚ä¸€æ–¹ã§ã€ã‚‚ã—ã‚³ãƒ³ãƒˆãƒ­ãƒ¼ãƒ«ãƒ‘ãƒãƒ«ã‹ã‚‰ã®åˆ¶å¾¡ã‚’ã‚»ã‚­ãƒ¥ãƒªãƒ†ã‚£ãƒ¢ãƒ‡ãƒ«ã§åˆ¶é™ã§ãã‚‹ã®ã§ã‚ã‚Œã°ã€ãã®åˆ¶å¾¡ã¯ã€Œãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã€ã§ã¯ã‚ã‚Šã¾ã›ã‚“ã€‚ã¾ãŸã€å®‰å…¨è¨­è¨ˆã®è¦³ç‚¹ã‚„æ©Ÿå™¨ã®ç‰©ç†æ§‹é€ ã®åˆ¶ç´„ã«ã‚ˆã‚Šç¦æ­¢ã™ã‚‹ã“ã¨ãŒã§ããªã„ã€Œãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã€ã‚‚ã‚ã‚Šå¾—ã‚‹ã“ã¨ã«æ³¨æ„ã—ã¦ãã ã•ã„ã€‚ä¾‹ãˆã°é›»æºä¾›çµ¦ã‚’ç‰©ç†çš„ã«åˆ‡æ–­ã™ã‚‹æ­¢ã‚ã‚‹ã‚ˆã†ãªã‚¿ã‚¤ãƒ—ã®ã‚¹ã‚¤ãƒƒãƒã«ã‚ˆã‚‹åˆ¶å¾¡ã¯ã€Œãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã€ã®å®šç¾©ã«å«ã¾ã‚Œã¾ã™ãŒã€ã“ã®åˆ¶å¾¡ã‚’ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã‚’ä»‹ã—ã¦ç¦æ­¢ã™ã‚‹ã“ã¨ã¯æœŸå¾…ã§ãã¾ã›ã‚“ã€‚</md:description>
    <md:description xml:lang="en">This interface provides the configuration function for enable of device local control.  The term of "local control" is means the control operation that cannot be limited by security model of the networking. For example, it is a "local control" generally that the control operation using physical button on the device, infrared remote controller, or control panel.  On the other hand, it is not a "local control" if the control operation can be limited by security model of the networking.  It should be noted that there may be some "local control" of each device that can not be limited using this interface for security reason or the design limitation of the device.  For example, the operation using the physical switch of the device to cut the power supply is a "local control" definitely but it cannot be limited by using this interface.</md:description>
    <typedefs>
        <boolean type-id="0">
            <md:display-name xml:lang="ja">Flag</md:display-name>
            <md:display-name xml:lang="en">Flag</md:display-name>
            <md:description xml:lang="ja">è¨±å¯çŠ¶æ…‹ã‚’ç¤ºã™ãƒ•ãƒ©ã‚°ã€‚</md:description>
            <md:description xml:lang="en">A flag indicates enable.</md:description>
        </boolean>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="true">
            <md:display-name xml:lang="ja">enable flag</md:display-name>
            <md:display-name xml:lang="en">is enable</md:display-name>
            <md:description xml:lang="ja">ãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡è¨±å¯ãƒ•ãƒ©ã‚°ã€‚trueã§ã‚ã‚Œã°è¨±å¯ã€‚falseã§ã‚ã‚Œã°ä¸è¨±å¯ã€‚</md:description>
            <md:description xml:lang="en">The flag indicates local control enable. If this flag is TRUE, local control for the device is enabled.</md:description>
        </property>
    </properties>
    <exceptions>
        <exception item-id="2">
            <md:display-name xml:lang="ja">Cannot be enable</md:display-name>
            <md:display-name xml:lang="en">Cannot be enabled</md:display-name>
            <md:description xml:lang="ja">ä½•ã‚‰ã‹ã®ç†ç”±ã§ä¸€æ™‚çš„ã«ãƒ­ãƒ¼ã‚«ãƒ«åˆ¶å¾¡ã‚’è¨±å¯ã«ã§ããªã„ã“ã¨ã‚’ç¤ºã™ä¾‹å¤–ã€‚ã“ã®ä¾‹å¤–ã¯"enable flag"ã‚’trueã«è¨­å®šã—ã‚ˆã†ã¨ã—ãŸéš›ã«ç™ºç”Ÿã™ã‚‹å¯èƒ½æ€§ãŒã‚ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">The exception indicates that the flag cannot be enabled temporarily for some reason (for example to be safety).</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:02','2015-09-21 18:07:01',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (38,'[1:{08}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{08}]">
    <md:display-name xml:lang="en">Program Control</md:display-name>
    <md:description xml:lang="en">This interface models the type of program control defined by initd on Linux operating systems. This interface can be exposed on any object that exhibits the same type of control (start, stop, restart). All methods may return once the request is accepted and before the state change has completed. ''Request Failed'' may be returned as an exception from all of the methods.</md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="3">
            <md:display-name xml:lang="en">State</md:display-name>
            <md:description xml:lang="en">The current state of the program. The following values are valid with the given meanings: (0) STOPPED, (1) STARTING, (2) RUNNING, (3) STOPPING.</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="0" type-ref="0" read="true" write="false">
            <md:display-name xml:lang="en">Program State</md:display-name>
            <md:description xml:lang="en">Return the current operating state of the program.</md:description>
        </property>
    </properties>
    <methods>
        <method item-id="1">
            <md:display-name xml:lang="en">Start</md:display-name>
            <md:description xml:lang="en">Instruct the program to start running.</md:description>
        </method>
        <method item-id="2">
            <md:display-name xml:lang="en">Stop</md:display-name>
            <md:description xml:lang="en">Instruct the program to stop running.</md:description>
        </method>
        <method item-id="3">
            <md:display-name xml:lang="en">Restart</md:display-name>
            <md:description xml:lang="en">Instruct the program to restart (stop followed by a start).</md:description>
        </method>
    </methods>
    <exceptions>
        <exception item-id="4">
            <md:display-name xml:lang="en">Request Failed</md:display-name>
            <md:description xml:lang="en">Returned from any of the methods if the request failed to initiate or complete the state change as requested. The current state after a failure may not be the same as the state when the request was received and should be verified by reading the current state.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:03','2015-09-21 18:07:00',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (39,'[1:{0227}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0227}]">
    <md:code-name>TOPOLOGYINFO</md:code-name>
    <md:display-name xml:lang="en">Topology Information</md:display-name>
    <md:description xml:lang="en">
        This interface provides bulk topology information useful to ''bootstrap'' a topology index. The provider of this information does not necessarily correspond to a source or a sink. In order to provide some size limitations, the amount of information that can be passed in a single response is limited. If a node has more information than can be provided in a single response, it should use multiple objects. It can use any method to determine which object identifiers it wants to use - the responding object is not important to the requester.
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000028">Data Topology Update</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <oid type-id="1">
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID</md:display-name>
            <md:description xml:lang="en">An identifier for an actor in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <nullable type-id="2" type-ref="1">
            <md:code-name>OptionalID</md:code-name>
            <md:display-name xml:lang="en">Optional ID</md:display-name>
            <md:description xml:lang="en">An optional ID that is used in cases (for example, parent) where the ID may not exist. NULL indicates that there is no ID.</md:description>
        </nullable>
        <uint16 type-id="3">
            <md:code-name>Position</md:code-name>
            <md:display-name xml:lang="en">Position</md:display-name>
            <md:description xml:lang="en">The logical position of the device relative to the parent. The parent defines the context/meaning of the positions for its children. A parent may have multiple children with the same position, which may indicate a grouping or relationship between those children. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
        </uint16>
        <nullable type-id="4" type-ref="3">
            <md:code-name>OptionalPosition</md:code-name>
            <md:display-name xml:lang="en">Optional Position</md:display-name>
            <md:description xml:lang="en">An optional position is used in cases where the position may not exist. NULL indicates that there is no position.</md:description>
        </nullable>
        <structure type-id="5">
            <md:code-name>Topology</md:code-name>
            <md:display-name xml:lang="en">Topology</md:display-name>
            <md:description xml:lang="en">A structure containing topology information about a node.</md:description>
            <field type-ref="1">
                <md:display-name xml:lang="en">id</md:display-name>
                <md:description xml:lang="en">The ID of the node.</md:description>
            </field>
            <field type-ref="2">
                <md:display-name xml:lang="en">parent</md:display-name>
                <md:description xml:lang="en">The ID of the parent of the device, if applicable.  The parent is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
            <field type-ref="4">
                <md:display-name xml:lang="en">position</md:display-name>
                <md:description xml:lang="en">The position of the device relative to the parent, if applicable. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
            </field>
        </structure>
        <array type-id="6" type-ref="5" min-length="1" length="1000">
            <md:code-name>TopologyArray</md:code-name>
            <md:display-name xml:lang="en">TopologyArray</md:display-name>
            <md:description xml:lang="en">An array of topology information from a source. If all the information cannot be put in a single array then multiple providers must be created.</md:description>
        </array>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>GetTopologyInfo</md:code-name>
            <md:display-name xml:lang="en">Get Topology Info</md:display-name>
            <md:description xml:lang="en">Returns an array of topology info from a source. If a source has more topology info than will fit in the array then multiple providers are required.</md:description>
            <inputs>
                <input type-ref="1">
                    <md:display-name xml:lang="en">node</md:display-name>
                    <md:description xml:lang="en">An ID to specify the desired result set. The broadcast OID indicates that all information should be returned. If a specific ID is used, then only information about that ID should be included. In this case no response would be generated by nodes that do not have relevant information.</md:description>
                </input>
            </inputs>
            <outputs>
                <output type-ref="1">
                    <md:display-name xml:lang="en">source</md:display-name>
                    <md:description xml:lang="en">The ID of the source that manages the devices.</md:description>
                </output>
                <output type-ref="6">
                    <md:display-name xml:lang="en">topologyInfo</md:display-name>
                    <md:description xml:lang="en">An array of topology information related to the source.</md:description>
                </output>
            </outputs>
        </method>
    </methods>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:03','2015-09-21 18:06:59',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (40,'[1:{01000028}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000028}]">
    <md:code-name>TOPOLOGYUPDATE</md:code-name>
    <md:display-name xml:lang="en">Topology Update</md:display-name>
    <md:description xml:lang="en">
        This interface is used to notify a provider about topology changes. Topology information is typically updated as data is delivered. However, there are cases where it is desirable to simply be notified of topology changes. This interface implies the requestor maintains state of the topology, thus knowning the previous state of the device in addition to the updated state.
  	    <![CDATA[<br><h3>Associated Interfaces</h3>
        <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000022">Data Manager</a></li>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.0227">Topology Info</a></li>
        </ul>]]>
    </md:description>
    <typedefs>
        <oid type-id="0">
            <md:code-name>ID</md:code-name>
            <md:display-name xml:lang="en">ID</md:display-name>
            <md:description xml:lang="en">An identifier for an actor in the data exchange (device, source, sink, parent).</md:description>
        </oid>
        <nullable type-id="1" type-ref="0">
            <md:code-name>OptionalID</md:code-name>
            <md:display-name xml:lang="en">Optional ID</md:display-name>
            <md:description xml:lang="en">An optional ID that is used in cases (for example, parent) where the ID may not exist. NULL indicates that there is no ID.</md:description>
        </nullable>
        <datetime type-id="2">
            <md:code-name>Timestamp</md:code-name>
            <md:display-name xml:lang="en">Timestamp</md:display-name>
            <md:description xml:lang="en">The time at which the topology change occurred.</md:description>
        </datetime>
        <uint16 type-id="3">
            <md:code-name>Position</md:code-name>
            <md:display-name xml:lang="en">Position</md:display-name>
            <md:description xml:lang="en">The logical position of the device relative to the parent. The parent defines the context/meaning of the positions for its children. A parent may have multiple children with the same position, which may indicate a grouping or relationship between those children. The position is meta-data associated with the device itself, and not related directly to data or an event.</md:description>
        </uint16>
        <nullable type-id="4" type-ref="2">
            <md:code-name>OptionalPosition</md:code-name>
            <md:display-name xml:lang="en">Optional Position</md:display-name>
            <md:description xml:lang="en">An optional position is used in cases where the position may not exist. NULL indicates that there is no position.</md:description>
        </nullable>
    </typedefs>
    <methods>
        <method item-id="1">
            <md:code-name>parentChanged</md:code-name>
            <md:display-name xml:lang="en">Parent Changed</md:display-name>
            <md:description xml:lang="en">Send notification that the parent of the device has changed.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:code-name>device</md:code-name>
                    <md:display-name xml:lang="en">device</md:display-name>
                    <md:description xml:lang="en">The device for which the parent has changed.</md:description>
                </input>
                <input type-ref="2">
                    <md:code-name>timestamp</md:code-name>
                    <md:display-name xml:lang="en">timestamp</md:display-name>
                    <md:description xml:lang="en">The time at which the parent changed.</md:description>
                </input>
                <input type-ref="1">
                    <md:code-name>original</md:code-name>
                    <md:display-name xml:lang="en">original</md:display-name>
                    <md:description xml:lang="en">The original (old) parent of the device.</md:description>
                </input>
                <input type-ref="1">
                    <md:code-name>update</md:code-name>
                    <md:display-name xml:lang="en">update</md:display-name>
                    <md:description xml:lang="en">The updated (new) parent of the device.</md:description>
                </input>
            </inputs>
        </method>
        <method item-id="2">
            <md:code-name>positionChanged</md:code-name>
            <md:display-name xml:lang="en">Position Changed</md:display-name>
            <md:description xml:lang="en">Send notification that the position of the device has changed.</md:description>
            <inputs>
                <input type-ref="0">
                    <md:code-name>device</md:code-name>
                    <md:display-name xml:lang="en">device</md:display-name>
                    <md:description xml:lang="en">The device for which the position has changed.</md:description>
                </input>
                <input type-ref="2">
                    <md:code-name>timestamp</md:code-name>
                    <md:display-name xml:lang="en">timestamp</md:display-name>
                    <md:description xml:lang="en">The time at which the position changed.</md:description>
                </input>
                <input type-ref="4">
                    <md:code-name>original</md:code-name>
                    <md:display-name xml:lang="en">original</md:display-name>
                    <md:description xml:lang="en">The original (old) position of the device.</md:description>
                </input>
                <input type-ref="4">
                    <md:code-name>update</md:code-name>
                    <md:display-name xml:lang="en">update</md:display-name>
                    <md:description xml:lang="en">The updated (new) position of the device.</md:description>
                </input>
            </inputs>
        </method>
    </methods>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:04','2015-09-21 18:06:56',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (41,'[1:{0B}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0B}]">
    <md:display-name xml:lang="ja">ç·ç¨¼åƒæ™‚é–“</md:display-name>
    <md:display-name xml:lang="en">Total operation time</md:display-name>
    <md:description xml:lang="ja">ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã¯ã€ç¾åœ¨ã¾ã§ã®æ©Ÿå™¨ã®ç·ç¨¼åƒæ™‚é–“æƒ…å ±ã‚’æä¾›ã—ã¾ã™ã€‚</md:description>
    <md:description xml:lang="en">This interface provides the total operation time of the device itself.</md:description>
    <typedefs>
        <uint64 type-id="0">
            <md:display-name xml:lang="ja">Seconds</md:display-name>
            <md:display-name xml:lang="en">Seconds</md:display-name>
            <md:description xml:lang="ja">ç§’æ•°ã€‚</md:description>
            <md:description xml:lang="en">Seconds.</md:description>
        </uint64>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:display-name xml:lang="ja">ç·ç¨¼åƒæ™‚é–“</md:display-name>
            <md:display-name xml:lang="en">total operation time</md:display-name>
            <md:description xml:lang="ja">ç¾åœ¨ã¾ã§ã®æ©Ÿå™¨ã®ç·ç¨¼åƒæ™‚é–“ã€‚ã“ã®å€¤ã¯å˜èª¿ã«ç´¯ç©ã•ã‚Œã‚‹å€¤ã§ã‚ã‚Šã€æ¸›å°‘ã™ã‚‹ã“ã¨ã‚„ãƒªã‚»ãƒƒãƒˆã•ã‚Œã‚‹ã“ã¨ã¯ãªã„ã€‚ãªãŠã€ç¨¼åƒæ™‚é–“ã¨ã¯å˜ã«æ©Ÿå™¨ãŒç”Ÿç”£ã•ã‚Œã¦ã‹ã‚‰ã®çµŒéŽæ™‚é–“ã§ã¯ãªãã€æ©Ÿå™¨ã®é›»æºã‚¹ã‚¤ãƒƒãƒãŒONã«ãªã£ã¦ã„ã‚‹æ™‚é–“ã‚’æ„å‘³ã™ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">The total operation time of the device. This value is cumulative over the lifetime of the device.  This value cannot be decreased and cannot be reset also. In addition, the meaning of the term of "operation time" is the time that power switch of the device is turned ON.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:04','2015-09-21 18:07:02',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (42,'[1:{0100000A}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{0100000A}]">
    <md:code-name>TRANSPORT_DEMO</md:code-name>
    <md:display-name xml:lang="ja">é€šä¿¡ãƒ‡ãƒ¢ãƒ³ã‚¹ãƒˆãƒ¬ãƒ¼ã‚·ãƒ§ãƒ³ç”¨ã‚¤ãƒ³ã‚¿ãƒ•ã‚§ãƒ¼ã‚¹</md:display-name>
    <md:display-name xml:lang="en">Transport Demo Interface</md:display-name>
    <md:description xml:lang="ja">ä½Žé€ŸDLCï¼ŒIEEE802.15.4ã‚„ï¼ŒLEDã‚’æ­è¼‰ã™ã‚‹ä»®æƒ³æ©Ÿå™¨ã‚‚ã—ãã¯ä»®æƒ³LEDè¡¨ç¤ºå™¨ã«ã‚ˆã‚Šæ§‹æˆã•ã‚Œã‚‹ã€Œé€šä¿¡ãƒ‡ãƒ¢ã‚­ãƒƒãƒˆã€ã«ã¦ï¼Œã“ã®ã‚¤ãƒ³ã‚¿ãƒ•ã‚§ãƒ¼ã‚¹ã‚’ç”¨ã„ã¾ã™ï¼Ž</md:description>
    <md:description xml:lang="en">This interface is associated with the Transport Demonstration Kit, which includes LoDLC, 802.15.4, and virtual devices that have LED or virtual LED indicators.</md:description>
    <typedefs>
        <uint8 type-id="0">
            <md:code-name>LED</md:code-name>
            <md:display-name xml:lang="ja">LED</md:display-name>
            <md:display-name xml:lang="en">LED</md:display-name>
            <md:description xml:lang="ja">LEDã®çŠ¶æ…‹ã¨ã—ã¦å®šç¾©ã•ã‚Œã¾ã™ï¼Žå€¤ãŒ0ã®ã¨ãæ¶ˆç¯çŠ¶æ…‹ã§ï¼Œ255ã®ã¨ãæœ€å¤§å‡ºåŠ›ç‚¹ç¯çŠ¶æ…‹ã§ã™ï¼Žæ˜Žã‚‹ã•åˆ¶å¾¡ã‚’å®Ÿç¾ã™ã‚‹LEDå›žè·¯ã‚’æŒã¤å ´åˆã¯ï¼Œç·šå½¢è£œé–“ã•ã‚ŒãŸæ˜Žã‚‹ã•ã§ä¸­é–“å€¤ã‚’å®Ÿè£…ã™ã‚‹å¿…è¦ãŒã‚ã‚Šã¾ã™ï¼Žä¸€æ–¹ã§ï¼Œæ˜Žã‚‹ã•åˆ¶å¾¡ã‚’å®Ÿç¾ã™ã‚‹å›žè·¯ã‚’æŒãŸãªã„æ©Ÿå™¨ã®å ´åˆã¯ï¼Œ0ã‚ˆã‚Šå¤§ãã„å€¤ã¯ï¼Œæœ€å¤§å‡ºåŠ›ç‚¹ç¯çŠ¶æ…‹ã¨ã—ã¦å–ã‚Šæ‰±ã†å¿…è¦ãŒã‚ã‚Šã¾ã™ï¼Ž</md:description>
            <md:description xml:lang="en">Defines the state of an LED. 0=off. 255=fully on. Intermediate values must be implemented in a logical linear fashion for LED circuits that support variable brightnesses. Devices that have LED circuits that do not support variable brightnesses must treat all values greater than 0 as fully on.</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="true">
            <md:code-name>LEDState</md:code-name>
            <md:display-name xml:lang="ja">LED çŠ¶æ…‹</md:display-name>
            <md:display-name xml:lang="en">LED State</md:display-name>
            <md:description xml:lang="ja">æ©Ÿå™¨ä¸Šã®LEDçŠ¶æ…‹ï¼Ž</md:description>
            <md:description xml:lang="en">The state of the LED on a device.</md:description>
        </property>
    </properties>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:05','2015-09-21 18:06:05',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (43,'[1:{01000001}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000001}]">
    <md:code-name>IWAVEFORM</md:code-name>
    <md:display-name xml:lang="ja">ãƒˆãƒ¬ãƒ¼ãƒ‹ãƒ³ã‚°ç”¨æ³¢å½¢ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹</md:display-name>
    <md:display-name xml:lang="en">Training Waveform Interface</md:display-name>
    <md:description xml:lang="ja">
        ã“ã®ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ãƒ¼ã‚¹ã¯ã€ãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã‚’éžå¸¸ã«ç°¡å˜ã«å®Ÿè£…ã™ã‚‹ãŸã‚ã®æ‰‹æ®µã‚’æä¾›ã™ã‚‹ã€‚ãƒˆãƒ¬ãƒ¼ãƒŠãƒ¼ç”¨ãƒ™ãƒ¼ã‚¹ã‚¤ãƒ³ã‚¿ãƒ¼ãƒ•ã‚§ã‚¤ã‚¹ã¨ä½µç”¨ã™ã‚‹å ´åˆã¯ã€ã‚»ãƒƒã‚·ãƒ§ãƒ³ã¨ã—ã¦æä¾›ã™ã‚‹ã“ã¨ãŒå¯èƒ½ã§ã‚ã‚‹ã€‚
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000000">Training Base</a></li>
   	    </ul>]]>
</md:description>
    <md:description xml:lang="en">
        This interface provides easy-to-implement properties. It can be provided as a session if used in conjunction with the Training Base Interface.
        <![CDATA[<br>
        <h3>Associated Interfaces</h3>
   	    <ul>
            <li><a href="https://interface.opendof.org/interface-repository/interface?cmd=GetInterface&trans=html&iid=dof/1.01000000">Training Base</a></li>
   	    </ul>]]>
    </md:description>
    <typedefs>
        <uint8 type-id="0">
            <md:code-name>Value</md:code-name>
            <md:display-name xml:lang="ja">Value</md:display-name>
            <md:display-name xml:lang="en">Value</md:display-name>
            <md:description xml:lang="ja">æ³¢ã®å€¤ã§ã™ã€‚å˜ä½ã®ãªã„ã“ã®ç•ªå·ã€‚</md:description>
            <md:description xml:lang="en">A value of the wave. This a number without units.</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>Position</md:code-name>
            <md:display-name xml:lang="ja">Position</md:display-name>
            <md:display-name xml:lang="en">Position</md:display-name>
            <md:description xml:lang="ja">æ³¢å½¢ã®ç¾åœ¨ã®ä½ç½®ã€‚ã“ã®ä½ç½®ã¯ã€Minimumãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã¨Maximumãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã¨ã®é–“ã‚’ç§»å‹•ã™ã‚‹ã€‚</md:description>
            <md:description xml:lang="en">The current value of the waveform, which will change between the Minimum property and the Maximum property.</md:description>
        </property>
        <property item-id="2" type-ref="0" read="true" write="true">
            <md:code-name>Minimum</md:code-name>
            <md:display-name xml:lang="ja">Minimum</md:display-name>
            <md:display-name xml:lang="en">Minimum</md:display-name>
            <md:description xml:lang="ja">æ³¢å½¢ã®æœ€å°æŒ¯å¹…ã€‚ã“ã®æ•°å€¤ã¯ã€Maximumãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ä»¥ä¸‹ã§ãªã‘ã‚Œã°ãªã‚‰ãªã„ã€‚</md:description>
            <md:description xml:lang="en">The minimum amplitude of the waveform. It must be less than or equal to the Maximum property.</md:description>
        </property>
        <property item-id="3" type-ref="0" read="true" write="true">
            <md:code-name>Maximum</md:code-name>
            <md:display-name xml:lang="ja">Maximum</md:display-name>
            <md:display-name xml:lang="en">Maximum</md:display-name>
            <md:description xml:lang="ja">æ³¢å½¢ã®æœ€å¤§æŒ¯å¹…ã€‚ã“ã®æ•°å€¤ã¯ã€Minimumãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ä»¥ä¸Šã§ãªã‘ã‚Œã°ãªã‚‰ãªã„ã€‚</md:description>
            <md:description xml:lang="en">The maximum amplitude of the waveform. It must be greater than or equal to the Minimum property.</md:description>
        </property>
    </properties>
    <exceptions>
        <exception item-id="4">
            <md:code-name>InvalidMinMax</md:code-name>
            <md:display-name xml:lang="ja">Invalid Min/Max</md:display-name>
            <md:display-name xml:lang="en">Invalid Min/Max</md:display-name>
            <md:description xml:lang="ja">ãƒªã‚¯ã‚¨ã‚¹ã‚¿ãŒç„¡åŠ¹ãªMinimum ãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã¾ãŸã¯ Maximumãƒ—ãƒ­ãƒ‘ãƒ†ã‚£ã‚’ã‚»ãƒƒãƒˆã—ã‚ˆã†ã¨ã—ãŸå ´åˆã«å‡ºåŠ›ã•ã‚Œã‚‹ä¾‹å¤–ã€‚</md:description>
            <md:description xml:lang="en">This is thrown if a requestor attempts to set an invalid Minimum or Maximum property.</md:description>
        </exception>
    </exceptions>
</interface>
','1','opendof',71,73,'2015-09-21 18:06:06','2015-09-21 18:06:06',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (44,'[1:{01000024}]','<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[01:{01000024}]">
	<md:code-name>DEW_POINT_TEMPERATURE_INTERFACE</md:code-name>
	<md:display-name xml:lang="en">Dew Point Temperature</md:display-name>
	<md:description xml:lang="en">
		This interface is used to record the dew point temperature of a weather station.
	</md:description>
		<typedefs>
			<float32 type-id="0">
				<md:code-name>Temperature</md:code-name>
				<md:display-name xml:lang="en">Temperature</md:display-name>
				<md:description xml:lang="en">
					A measure of temperature.
				</md:description>
				<unit>Celsius</unit>
			</float32>
		</typedefs>
	<properties>
		<property item-id="1" type-ref="0" read="true" write="false">
			<md:code-name>dewPointTemperature</md:code-name>
			<md:display-name xml:lang="en">Dew Point Temperature</md:display-name>
			<md:description xml:lang="en">The dew point temperature.</md:description>
		</property>
	</properties>
</interface>
','1','opendof',10,null,'2015-10-01 15:22:40','2015-10-01 15:22:40',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (45,'[1:{01000025}]','<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[01:{01000025}]">
	<md:code-name>DRY_BULB_TEMPERATURE_INTERFACE</md:code-name>
	<md:display-name xml:lang="en">Dry Bulb Temperature</md:display-name>
	<md:description xml:lang="en">
		This interface is used to record the dry bulb temperature of a weather station.
	</md:description>
		<typedefs>
			<float32 type-id="0">
				<md:code-name>Temperature</md:code-name>
				<md:display-name xml:lang="en">Temperature</md:display-name>
				<md:description xml:lang="en">
					A measure of temperature.
				</md:description>
				<unit>Celsius</unit>
			</float32>
		</typedefs>
	<properties>
		<property item-id="1" type-ref="0" read="true" write="false">
			<md:code-name>dryBulbTemperature</md:code-name>
			<md:display-name xml:lang="en">Dry Bulb Temperature</md:display-name>
			<md:description xml:lang="en">The dry bulb temperature.</md:description>
		</property>
	</properties>
</interface>
','1','opendof',10,null,'2015-10-01 15:22:47','2015-10-01 15:22:47',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (46,'[1:{0100002E}]','<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[01:{0100002E}]">
	<md:code-name>POWER_READING_INTERFACE</md:code-name>
	<md:display-name xml:lang="en">Power Reading</md:display-name>
	<md:description xml:lang="en">
		This interface is used to record the power readings for a home.
	</md:description>
		<typedefs>
			<float32 type-id="0">
				<md:code-name>ApparentPower</md:code-name>
				<md:display-name xml:lang="en">Apparent Power</md:display-name>
				<md:description xml:lang="en">
					A measure of apparent power.
				</md:description>
				<unit>Volt-Amperes</unit>
			</float32>
		</typedefs>
	<properties>
		<property item-id="1" type-ref="0" read="true" write="false">
			<md:code-name>powerReading</md:code-name>
			<md:display-name xml:lang="en">Power Reading</md:display-name>
			<md:description xml:lang="en">The power reading of a single channel.</md:description>
		</property>
	</properties>
</interface>
','1','opendof',10,null,'2015-10-01 15:22:48','2015-10-01 15:22:48',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (47,'[1:{01000026}]','<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[01:{01000026}]">
	<md:code-name>WET_BULB_TEMPERATURE_INTERFACE</md:code-name>
	<md:display-name xml:lang="en">Wet Bulb Temperature</md:display-name>
	<md:description xml:lang="en">
		This interface is used to record the wet bulb temperature.
	</md:description>
		<typedefs>
			<float32 type-id="0">
				<md:code-name>Temperature</md:code-name>
				<md:display-name xml:lang="en">Temperature</md:display-name>
				<md:description xml:lang="en">
					A measure of temperature.
				</md:description>
				<unit>Celsius</unit>
			</float32>
		</typedefs>
	<properties>
		<property item-id="1" type-ref="0" read="true" write="false">
			<md:code-name>wetBulbTemperature</md:code-name>
			<md:display-name xml:lang="en">Wet Bulb Temperature</md:display-name>
			<md:description xml:lang="en">The wet bulb temperature.</md:description>
		</property>
	</properties>
</interface>
','1','opendof',10,null,'2015-10-01 15:22:49','2015-10-01 15:22:49',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (48,'[1:{01000058}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000058}]">
    <md:code-name>ButtonDemoConfiguration</md:code-name>
    <md:display-name xml:lang="en">Button Demo Configuration Interface</md:display-name>
    <md:description xml:lang="en">Basic connectivity topology; a gateway in the cloud, a local gateway, and devices that are on the same network as the local gateway.</md:description>
    <typedefs>
        <uint8 type-id="0">
            <md:code-name>buttonCount</md:code-name>
            <md:display-name xml:lang="en">buttonCount</md:display-name>
            <md:description xml:lang="en">buttonCount. UInt8 with a range of 0 to 32767.</md:description>
        </uint8>
		<string type-id="1" encoding="3" length="64">
			<md:code-name>ipAddress</md:code-name>
			<md:display-name xml:lang="en">ipAddress</md:display-name>
			<md:description xml:lang="en">ipAddress.  String with a maximum length of 64 characters.</md:description>
		</string>
		<nullable type-id="2" type-ref="1">
			<md:code-name>optionalIpAddress</md:code-name>
			<md:display-name xml:lang="en">optionalIpAddress</md:display-name>
			<md:description xml:lang="en">optionalIpAddress.  Nullable String with a maximum length of 64 characters if given.</md:description>
		</nullable>
        <uint16 type-id="3">
            <md:code-name>port</md:code-name>
            <md:display-name xml:lang="en">port</md:display-name>
            <md:description xml:lang="en">port. UInt16 with a range of 0 to 2147483647.</md:description>
        </uint16>
		<nullable type-id="4" type-ref="3">
			<md:code-name>optionalPort</md:code-name>
			<md:display-name xml:lang="en">optionalPort</md:display-name>
			<md:description xml:lang="en">optionalPort.  Nullable Uint16 with a range of 0 to 2147483647 if given.</md:description>
		</nullable>
		<oid type-id="5">
			<md:code-name>groupAddress</md:code-name>
			<md:display-name xml:lang="en">groupAddress</md:display-name>
			<md:description xml:lang="en">groupAddress.  A DOFObjectID which identifies the group.</md:description>
		</oid>
		<nullable type-id="6" type-ref="5">
			<md:code-name>optionalGroupAddress</md:code-name>
			<md:display-name xml:lang="en">optionalGroupAddress</md:display-name>
			<md:description xml:lang="en">optionalGroupAddress.  Nullable DOFObjectID which identifies the optional group if given.</md:description>
		</nullable>
        <uint8 type-id="7">
            <md:code-name>connectionType</md:code-name>
            <md:display-name xml:lang="en">connectionType</md:display-name>
            <md:description xml:lang="en">connectionType. UInt8 with a range of 0 to 32767.</md:description>
        </uint8>
        <uint8 type-id="8">
            <md:code-name>buttonNumber</md:code-name>
            <md:display-name xml:lang="en">buttonNumber</md:display-name>
            <md:description xml:lang="en">buttonNumber. UInt8 with a range of 0 to 32767.</md:description>
        </uint8>
        <uint8 type-id="9">
            <md:code-name>channel</md:code-name>
            <md:display-name xml:lang="en">channel</md:display-name>
            <md:description xml:lang="en">channel. UInt8 with a range of from 0 to 32767.</md:description>
        </uint8>
		<string type-id="10" encoding="3" length="32">
			<md:code-name>message</md:code-name>
			<md:display-name xml:lang="en">message</md:display-name>
			<md:description xml:lang="en">message.  String with a maximum length allowed of 32 characters.</md:description>
		</string>
   		<string type-id="11" encoding="3" length="32">
			<md:code-name>wifiSsid</md:code-name>
			<md:display-name xml:lang="en">wifiSsid</md:display-name>
			<md:description xml:lang="en">wifiSsid.  String with a maximum length allowed of 32 characters.</md:description>
		</string>
   		<string type-id="12" encoding="3" length="32">
			<md:code-name>wifiPassword</md:code-name>
			<md:display-name xml:lang="en">wifiPassword</md:display-name>
			<md:description xml:lang="en">wifiPassword.  String with a maximum length allowed of 32 characters.</md:description>
		</string>
    </typedefs>
	<properties>
		<property item-id="0" type-ref= "0" read="true" write="false">
				<md:code-name>buttonCount</md:code-name>
				<md:display-name xml:lang="en">buttonCount</md:display-name>
				<md:description xml:lang="en">The read only button count.</md:description>
		</property>
		<property item-id="1" type-ref= "9" read="true" write="true">
				<md:code-name>channel</md:code-name>
				<md:display-name xml:lang="en">channel</md:display-name>
				<md:description xml:lang="en">The read/write channel.</md:description>
		</property>
	</properties>
	<methods>
		<method item-id="2">
			<md:code-name>tcpConnection</md:code-name>
			<md:display-name xml:lang="en">tcpConnection</md:display-name>
			<md:description xml:lang="en">void tcpConnection(String optionalIpAddress, UInt16 optionalPort).  Default ipAddress and port to be used if they are given as null.</md:description>
			<inputs>
				<input type-ref="2">
					<md:code-name>optionalIpAddress</md:code-name>
					<md:display-name xml:lang="en">optionalIpAddress</md:display-name>
					<md:description xml:lang="en">The IP address to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="4">
					<md:code-name>optionalPort</md:code-name>
					<md:display-name xml:lang="en">optionalPort</md:display-name>
					<md:description xml:lang="en">The port to connect to.  May be null if default is to be used.</md:description>
				</input>
			</inputs>
		</method>
		<method item-id="3">
			<md:code-name>udpConnection</md:code-name>
			<md:display-name xml:lang="en">udpConnection</md:display-name>
			<md:description xml:lang="en">void udpConnection(String optionalIpAddress, UInt16 optionalPort).  Default ipAddress and port to be used if they are given as null.</md:description>
			<inputs>
				<input type-ref="2">
					<md:code-name>optionalIpAddress</md:code-name>
					<md:display-name xml:lang="en">optionalIpAddress</md:display-name>
					<md:description xml:lang="en">The IP address to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="4">
					<md:code-name>optionalPort</md:code-name>
					<md:display-name xml:lang="en">optionalPort</md:display-name>
					<md:description xml:lang="en">The port to connect to.  May be null if default is to be used.</md:description>
				</input>
			</inputs>
		</method>
		<method item-id="4">
			<md:code-name>secureMulticastConnection</md:code-name>
			<md:display-name xml:lang="en">secureMulticastConnection</md:display-name>
			<md:description xml:lang="en">void secureMulticastConnection(String optionalIpAddress, Uint16 optionalPort, DOFObjectID optionalGroupAddress).  Default ipAddress, port and groupAddress to be used if they are given as null.</md:description>
			<inputs>
				<input type-ref="2">
					<md:code-name>optionalIpAddress</md:code-name>
					<md:display-name xml:lang="en">optionalIpAddress</md:display-name>
					<md:description xml:lang="en">The IP address to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="4">
					<md:code-name>optionalPort</md:code-name>
					<md:display-name xml:lang="en">optionalPort</md:display-name>
					<md:description xml:lang="en">The port to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="6">
					<md:code-name>optionalGroupAddress</md:code-name>
					<md:display-name xml:lang="en">optionalGroupAddress</md:display-name>
					<md:description xml:lang="en">The SGMP Group address to connect to.  May be null if default is to be used.</md:description>
				</input>
			</inputs>
		</method>
		<method item-id="5">
			<md:code-name>displayConfiguration</md:code-name>
			<md:display-name xml:lang="en">displayConfiguration</md:display-name>
			<md:description xml:lang="en">void displayConfiguration().  Causes a device to display its configuration.</md:description>
		</method>
		<method item-id="6">
			<md:code-name>getConfiguration</md:code-name>
			<md:display-name xml:lang="en">getConfiguration</md:display-name>
			<md:description xml:lang="en">void getConfiguration(String optionalIpAddress, UInt16 optionalPort, DOFObjectID optionalGroupAddress, UInt8 connectionType).  Causes a device to display its configuration.</md:description>
			<outputs>
				<input type-ref="2">
					<md:code-name>optionalIpAddress</md:code-name>
					<md:display-name xml:lang="en">optionalIpAddress</md:display-name>
					<md:description xml:lang="en">The IP address to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="4">
					<md:code-name>optionalPort</md:code-name>
					<md:display-name xml:lang="en">optionalPort</md:display-name>
					<md:description xml:lang="en">The port to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="6">
					<md:code-name>optionalGroupAddress</md:code-name>
					<md:display-name xml:lang="en">optionalGroupAddress</md:display-name>
					<md:description xml:lang="en">The SGMP Group address to connect to.  May be null if default is to be used.</md:description>
				</input>
				<input type-ref="7">
					<md:code-name>connectionType</md:code-name>
					<md:display-name xml:lang="en">connectionType</md:display-name>
					<md:description xml:lang="en">The connection type.</md:description>
				</input>
			</outputs>
		</method>
		<method item-id="7">
			<md:code-name>saveConfiguration</md:code-name>
			<md:display-name xml:lang="en">saveConfiguration</md:display-name>
			<md:description xml:lang="en">void saveConfiguration().  Causes a device to save its configuration.</md:description>
		</method>
		<method item-id="8">
			<md:code-name>setButtonMessage</md:code-name>
			<md:display-name xml:lang="en">setButtonMessage</md:display-name>
			<md:description xml:lang="en">void setButtonMessage(UInt8 buttonNumber, String message).  Causes a message to be associated with a given button.</md:description>
			<inputs>
				<input type-ref="8">
					<md:code-name>buttonNumber</md:code-name>
					<md:display-name xml:lang="en">buttonNumber</md:display-name>
					<md:description xml:lang="en">The buttonNumber to associate the message with.</md:description>
				</input>
				<input type-ref="10">
					<md:code-name>message</md:code-name>
					<md:display-name xml:lang="en">message</md:display-name>
					<md:description xml:lang="en">The message to associate with the given button.</md:description>
				</input>
			</inputs>
		</method>
		<method item-id="9">
			<md:code-name>configureWiFi</md:code-name>
			<md:display-name xml:lang="en">configureWiFi</md:display-name>
			<md:description xml:lang="en">void configureWiFi(String wifiSsid, String wifiPassword).  Configures the WiFi to the given ssid and password.</md:description>
			<inputs>
				<input type-ref="11">
					<md:code-name>wifiSsid</md:code-name>
					<md:display-name xml:lang="en">wifiSsid</md:display-name>
					<md:description xml:lang="en">The WiFi SSID to use.</md:description>
				</input>
				<input type-ref="10">
					<md:code-name>wifiPassword</md:code-name>
					<md:display-name xml:lang="en">wifiPassword</md:display-name>
					<md:description xml:lang="en">The password for the given SSID.</md:description>
				</input>
			</inputs>
		</method>
    </methods>
	<exceptions>
		<exception item-id="10">
			<md:code-name>ButtonOutOfRange</md:code-name>
			<md:display-name xml:lang="en">ButtonOutOfRange</md:display-name>
			<md:description xml:lang="en">The given button number was out of range</md:description>
		</exception>
	</exceptions>
</interface>
','1','opendof',71,73,'2016-03-24 14:28:43','2016-03-24 14:28:43',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (49,'[1:{01000057}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{01000057}]">
    <md:code-name>ButtonDemoMessage</md:code-name>
    <md:display-name xml:lang="en">Button Demo Message Interface</md:display-name>
    <md:description xml:lang="en">Provides a very basic message sending method.</md:description>
    <typedefs>
        <uint8 type-id="0">
            <md:code-name>channel</md:code-name>
            <md:display-name xml:lang="en">channel</md:display-name>
            <md:description xml:lang="en">channel. UInt8 with a range of from 0 to 32767.</md:description>
        </uint8>
		<string type-id="1" encoding="3" length="32">
			<md:code-name>message</md:code-name>
			<md:display-name xml:lang="en">message</md:display-name>
			<md:description xml:lang="en">message.  String with a maximum length allowed of 32 characters.</md:description>
		</string>
    </typedefs>
	<methods>
		<method item-id="0">
			<md:code-name>buttonMessage</md:code-name>
			<md:display-name xml:lang="en">buttonMessage</md:display-name>
			<md:description xml:lang="en">Button Demo Message Interface</md:description>
			<inputs>
				<input type-ref="0">
					<md:code-name>channel</md:code-name>
					<md:display-name xml:lang="en">channel</md:display-name>
					<md:description xml:lang="en">The channel (UInt8) that the message is associated with.</md:description>
				</input>
				<input type-ref="1">
					<md:code-name>message</md:code-name>
					<md:display-name xml:lang="en">message</md:display-name>
					<md:description xml:lang="en">The message.</md:description>
				</input>
			</inputs>
		</method>
	</methods>
</interface>
','1','opendof',71,73,'2016-03-24 14:28:44','2016-03-24 14:28:44',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (50,'[1:{0200}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (51,'[1:{0201}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (52,'[1:{0202}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (53,'[1:{0203}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (54,'[1:{0204}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (55,'[1:{0205}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (56,'[1:{0206}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (57,'[1:{0207}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (58,'[1:{0208}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (59,'[1:{0209}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (60,'[1:{020A}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (61,'[1:{020B}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (62,'[1:{020C}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (63,'[1:{020E}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (64,'[1:{020F}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (65,'[1:{0210}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (66,'[1:{0211}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (67,'[1:{0212}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (68,'[1:{0213}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (69,'[1:{021F}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (70,'[1:{0220}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (71,'[1:{0222}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (72,'[1:{0232}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (73,'[1:{0233}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (74,'[1:{0235}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (75,'[1:{0236}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (76,'[1:{0237}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (77,'[1:{0238}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (78,'[1:{01000034}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (79,'[1:{0100003B}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (80,'[1:{0100003D}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (81,'[1:{0100003E}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (82,'[1:{0100003F}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (83,'[1:{01000040}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (84,'[1:{01000041}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (85,'[1:{01000042}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (86,'[1:{01000043}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (87,'[1:{01000044}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (88,'[1:{01000045}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (89,'[1:{01000046}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (90,'[1:{01000047}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (91,'[1:{0100004F}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (92,'[1:{01000050}]',null,'1','opendof',71,73,'2016-05-26 12:22:37','2016-05-26 12:22:37',0);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (93,'[1:{00010000}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{00010000}]">
    <md:code-name>DEVICE_STATUS_INTERFACE</md:code-name>
    <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
    <md:display-name xml:lang="en">Status(testing publish update2)</md:display-name>
    <md:description xml:lang="ja">このインターフェースは機器によって提供され、これによって機器のステータスを確認できる。</md:description>
    <md:description xml:lang="en">This interface is provided by devices and allows a device''s status to be retrieved asfehasfheuas. Testing publish meta update. </md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>Status(testing publish update)</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">機器のステータス</md:description>
            <md:description xml:lang="en">The status of the device.(testing publish update)</md:description>
        </property>
    </properties>
</interface>
','1','opendof',77,73,'2016-05-26 14:29:47','2016-05-26 15:34:38',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (94,'[1:{00010001}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{00010001}]">
    <md:code-name>DEVICE_STATUS_INTERFACE</md:code-name>
    <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
    <md:display-name xml:lang="en">Status(testing publish update2)</md:display-name>
    <md:description xml:lang="ja">このインターフェースは機器によって提供され、これによって機器のステータスを確認できる。</md:description>
    <md:description xml:lang="en">This interface is provided by devices and allows a device''s status to be retrieved asfehasfheuas. Testing publish meta update. </md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
        <uint8 type-id="1" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>Status(testing publish update)</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">機器のステータス</md:description>
            <md:description xml:lang="en">The status of the device.(testing publish update)</md:description>
        </property>
    </properties>
</interface>
','1','opendof',77,73,'2016-05-26 16:09:23','2016-05-26 22:34:41',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (96,'[1:{00010002}]','<?xml version="1.0" encoding="UTF-8"?>
<interface xmlns="http://opendof.org/schema/interface-repository" xmlns:md="http://opendof.org/schema/interface-repository-meta" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://opendof.org/schema/interface-repository http://opendof.org/schema/interface-repository.xsd " iid="[1:{00010002}]">
    <md:code-name>DEVICE_STATUS_INTERFACE</md:code-name>
    <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
    <md:display-name xml:lang="en">Status(testing publish update2)</md:display-name>
    <md:description xml:lang="ja">このインターフェースは機器によって提供され、これによって機器のステータスを確認できる。</md:description>
    <md:description xml:lang="en">This interface is provided by devices and allows a device''s status to be retrieved asfehasfheuas. Testing publish meta update. </md:description>
    <typedefs>
        <uint8 type-id="0" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
        <uint8 type-id="1" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
        <uint8 type-id="2" min="0" max="2">
            <md:code-name>StatusType</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">ステータスの列挙型値：ステータスの値は、OK（0）、WARN（1）、ERROR（2）の3つがある。</md:description>
            <md:description xml:lang="en">An enumerated value for the status. There are three status values: OK (0), WARN (1), and ERROR (2).(testing publish update)</md:description>
        </uint8>
    </typedefs>
    <properties>
        <property item-id="1" type-ref="0" read="true" write="false">
            <md:code-name>Status(testing publish update)</md:code-name>
            <md:display-name xml:lang="ja">Status（ステータス）</md:display-name>
            <md:display-name xml:lang="en">Status(testing publish update)</md:display-name>
            <md:description xml:lang="ja">機器のステータス</md:description>
            <md:description xml:lang="en">The status of the device.(testing publish update)</md:description>
        </property>
    </properties>
</interface>
','1','opendof',77,73,'2016-05-26 22:56:39','2016-05-26 23:17:53',1);
insert into `interface_repository`.`interface`(`ifacePk`,`iid`,`xml`,`version`,`repotype`,`submitterFk`,`groupFk`,`creationDate`,`modifiedDate`,`published`) values (97,'[1:{00010003}]',null,'1','opendof',77,75,'2016-05-27 13:19:39','2016-05-27 13:19:39',0);
