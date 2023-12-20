create or replace procedure getBestNewList(
    p_cur1 out sys_refcursor,
    p_cur2 out sys_refcursor)

is

begin
    --각 뷰를 조회해서 결과를 out 변수에 저장
    open p_cur1 for select * from best_pro_view order by pseq desc;
    open p_cur2 for select * from new_pro_view order by pseq desc;
    
end;