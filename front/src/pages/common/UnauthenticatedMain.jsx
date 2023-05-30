import { Button } from "@mui/material";
import { useCallback } from "react";
import { Navigate, useNavigate } from "react-router";
import useSWR from "swr";
import fetcher from "@utils/fetcher";
import ListMainClub from "@components/club/ListMainClub";
import MainTop from "@layouts/common/MainTop";

const UnauthenticatedMain = () => {
  const { data: myData, mutate: mutateMe } = useSWR(
    `http://${import.meta.env.VITE_SPRING_HOST}/rest/user/login`,
    fetcher
  );

  const navigate = useNavigate();

  const onClickLogin = useCallback(() => {
    navigate("/user/login");
  }, [navigate]);

  const onClickRegistProfile = useCallback(() => {
    navigate(`/community/profile/${myData?.userNo}`);
  }, [navigate, myData]);

  console.log(myData);

  if (myData?.profileImg) {
    console.log(myData);
    return <Navigate to="/" />;
  }

  return (
    <>
      <MainTop />
      <div style={{ marginTop: "63px" }}></div>
      <ListMainClub />
      {!myData && <Button onClick={onClickLogin}>LogIn</Button>}
      {myData && !myData?.profileImg && (
        <Button onClick={onClickRegistProfile}>profileImg등록</Button>
      )}
    </>
  );
};

export default UnauthenticatedMain;
