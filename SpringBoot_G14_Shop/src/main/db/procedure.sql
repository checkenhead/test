create or replace procedure getBestNewList(
    p_cur1 out sys_refcursor,
    p_cur2 out sys_refcursor)

is

begin
    --�� �並 ��ȸ�ؼ� ����� out ������ ����
    open p_cur1 for select * from best_pro_view order by pseq desc;
    open p_cur2 for select * from new_pro_view order by pseq desc;
    
end;