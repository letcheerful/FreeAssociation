# FreeAssociation
Finding And remember favorite the GitHub`s users.

����� ���̺귯���� �����ӿ�ũ

Fresco : ������ �̹����� ���� �ε��� ���� ���.
RxJava : �񵿱� �̺�Ʈ�� ó���� ���� ���.
Retrofit, Okhttp3 : GitHub API�� ������ ���� ���.
Room : ���ã���� �������� ����.
Constraint-Layout : ����Ʈ �������� ������ ��ġ�� ���� ���.

����

MVC���� ������� Layout, Activity, Fragment�� View�� ������Ʈ�� �ϰ� ������� �˻��� ������ ���� Model�� 
DataLoader��� �̸����� �����Ͽ����ϴ�.
�׸��� Model�� View������ ������ ���� GitHubAssociator��� �̸����� Controller�� ��������ϴ�.

���� ���� ���� �� 

������ ���� ������� �κ��� GitHub�� �˻� �� Page ������ ������ �ε��� �����Ϸ��� �ߴ� �κ��̾����ϴ�. �׷��� 
API�� Ƚ�� ���Ѱ� �˻� �� Ű����� �����ϴ� �̸��� ã�� �� ������ �κ� ���� ������ �־� �� �κ��� ��� ����ڿ��� 
������ �� �ֵ��� UX�� �������� ����� �ʿ��մϴ�.

���� API, DB�˻� �� �����͸� ��� ������Ʈ�ϴ� ���·� ������ �Ǿ��µ� �̰��� ���� ����(�����͸� ����, �߰�, ����)��
 �̺�Ʈ�� �����Ͽ� View���� �ּ����� �κи��� ������Ʈ�ϵ��� �����ϸ� ���� �� �����ϴ�.
��,  ADD(Index, User) ,REMOVE(Index, User), UPDATE(index, User) �̺�Ʈ�� ������ �������� 
������Ʈ�ϰ� notifyItemChanged(int),notifyItemInserted(int) ,notifyItemRemoved(int) ,
notifyItemRangeChanged(int, int), notifyItemRangeInserted(int, int)
notifyItemRangeRemoved(int, int)�� ���� �޼ҵ带 ȣ���մϴ�.

���������� ������ �����ʹ� ��� ���ŵǱ� ������ pull-to-refresh ����� �߰��ϸ� ���� �� �����ϴ�.

