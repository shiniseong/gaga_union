import * as React from "react";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import ListMeeting from "@components/meeting/ListMeeting";
import ListClub from "@components/club/ListClub";
import ListMyClub from "@components/club/ListMyClub";

export default function MeetingClubTabs() {
  const [value, setValue] = React.useState("meeting");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box
      sx={{
        position: "fixed",
        marginTop: "50px",
        width: "100%",
        typography: "body1",
      }}
    >
      <TabContext value={value}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList onChange={handleChange} aria-label="lab API tabs example">
            <Tab label="모임" value="meeting" sx={{ minWidth: "50%" }} />
            <Tab label="클럽" value="club" sx={{ minWidth: "50%" }} />
          </TabList>
        </Box>
        <Box
          sx={{
            overflowY: "auto",
            maxHeight: "calc(100vh - 200px)",
          }}
        >
          <TabPanel value="meeting">
            <ListMeeting />
          </TabPanel>

          <TabPanel value="club">
            나오라고오오오
            <ListClub />
            <ListMyClub />
          </TabPanel>
        </Box>
      </TabContext>
    </Box>
  );
}
