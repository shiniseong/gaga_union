import useClubFormStore from "@hooks/club/useClubFormStore";
import useInput from "@hooks/common/useInput";
import { Button, TextField } from "@mui/material";
import { Box } from "@mui/system";
import fetcher from "@utils/fetcher";
import axios from "axios";
import React, { useCallback } from "react";
import { useNavigate } from "react-router";
import useSWR from "swr";

const AddClub1 = () => {
  const {
    mainCategoryNo,
    filterTag,
    clubName,
    clubIntro,
    clubRegion,
    filterGender,
    filterMinAge,
    filterMaxAge,
    clubMaxMemberNo,
    file,
    onChangeField,
    reset,
  } = useClubFormStore();

  const { data: myData, mutate: mutateMe } = useSWR(
    `http://${import.meta.env.VITE_SPRING_HOST}/rest/user/login`,
    fetcher
  );

  const navigate = useNavigate();
  const handleSubmit = useCallback(
    async (event) => {
      event.preventDefault();

      try {
        const formData = new FormData();

        formData.append("file", file);
        formData.append("mainCategoryNo", mainCategoryNo);
        formData.append("filterTag", filterTag);
        formData.append("clubName", clubName);
        formData.append("clubIntro", clubIntro);
        formData.append("clubRegion", clubRegion);
        formData.append("filterGender", filterGender);
        formData.append("filterMinAge", filterMinAge);
        formData.append("filterMaxAge", filterMaxAge);
        formData.append("clubMaxMemberNo", clubMaxMemberNo);
        formData.append("clubLeaderNo", myData.userNo);

        console.log(useClubFormStore.clubName);
        console.log(useClubFormStore.clubRegion);
        console.log(useClubFormStore.clubMaxMemberNo);

        const response = await axios.post(
          `http://${import.meta.env.VITE_SPRING_HOST}/rest/club`,
          formData
        );

        reset();

        navigate("/");

        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    },
    [filterGender, filterMinAge, filterMaxAge, clubMaxMemberNo]
  );

  return (
    <Box>
      <TextField
        fulWidth
        label="filterGender"
        name="filterGender"
        onChange={(e) => onChangeField("filterGender", e)}
        required
        value={filterGender}
      />
      <br />
      <TextField
        fulWidth
        label="filterMinAge"
        name="filterMinAge"
        onChange={(e) => onChangeField("filterMinAge", e)}
        required
        value={filterMinAge}
      />
      <br />
      <TextField
        fulWidth
        label="filterMaxAge"
        name="filterMaxAge"
        onChange={(e) => onChangeField("filterMaxAge", e)}
        required
        value={filterMaxAge}
      />
      <br />
      <TextField
        fulWidth
        label="clubMaxMemberNo"
        name="clubMaxMemberNo"
        onChange={(e) => onChangeField("clubMaxMemberNo", e)}
        required
        value={clubMaxMemberNo}
      />
      <br />
      <br />
      <br />

      <Button
        onClick={handleSubmit}
        variant="contained"
        color="primary"
        size="large"
      >
        생성하기
      </Button>
    </Box>
  );
};

export default AddClub1;
