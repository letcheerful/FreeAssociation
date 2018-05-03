# FreeAssociation
Finding And remember favorite the GitHub`s users.

사용한 라이브러리와 프레임워크

Fresco : 프로필 이미지의 동적 로딩을 위해 사용.
RxJava : 비동기 이벤트의 처리를 위해 사용.
Retrofit, Okhttp3 : GitHub API의 접근을 위해 사용.
Room : 즐겨찾기한 데이터의 저장.
Constraint-Layout : 리스트 아이템의 유연한 배치를 위해 사용.

설계

MVC모델을 기반으로 Layout, Activity, Fragment를 View의 컨포넌트로 하고 사용자의 검색과 저장을 위한 Model을 
DataLoader라는 이름으로 정의하였습니다.
그리고 Model과 View사이의 연결을 위해 GitHubAssociator라는 이름으로 Controller를 만들었습니다.

향후 개선 사항 등 

구현에 가장 어려웠던 부분은 GitHub의 검색 시 Page 단위의 데이터 로딩을 지원하려고 했던 부분이었습니다. 그러나 
API의 횟수 제한과 검색 시 키워드로 시작하는 이름만 찾을 수 없었던 부분 등의 제약이 있어 이 부분을 어떻게 사용자에게 
어필할 수 있도록 UX를 설계할지 고민이 필요합니다.

또한 API, DB검색 시 데이터를 모두 업데이트하는 형태로 구현이 되었는데 이것을 변경 내용(데이터를 삭제, 추가, 변경)을
 이벤트로 생성하여 View에서 최소한의 부분만을 업데이트하도록 변경하면 좋을 것 같습니다.
즉,  ADD(Index, User) ,REMOVE(Index, User), UPDATE(index, User) 이벤트를 받으면 아이템을 
업데이트하고 notifyItemChanged(int),notifyItemInserted(int) ,notifyItemRemoved(int) ,
notifyItemRangeChanged(int, int), notifyItemRangeInserted(int, int)
notifyItemRangeRemoved(int, int)와 같은 메소드를 호출합니다.

마지막으로 서버의 데이터는 계속 갱신되기 때문에 pull-to-refresh 기능을 추가하면 좋을 것 같습니다.

