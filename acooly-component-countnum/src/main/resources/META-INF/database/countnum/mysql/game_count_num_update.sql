
/***
 * 更新 计数组件表
 */
update game_count_num set type='NUM_DESC' where type='TIME_LIMIT';
update game_count_num set type='NUM_ASC' where type='NUM_LIMIT';


/***
 * * 更新 计数组件记录表
 */
update game_count_num_order set count_type='NUM_DESC' where count_type='TIME_LIMIT';
update game_count_num_order set count_type='NUM_ASC' where count_type='NUM_LIMIT';