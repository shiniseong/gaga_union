import * as React from "react";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import ListMeeting from "@components/meeting/ListMeeting";
import ListClub from "@components/club/ListClub";
import useCommonStore from "@stores/common/useCommonStore";
import ListMyClub from "@components/club/ListMyClub";

export default function MeetingClubTabs() {
  const { groupType, setField } = useCommonStore();

  const handleChange = (event, newValue) => {
    setField("groupType", newValue);
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
      <TabContext value={groupType}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList onChange={handleChange} aria-label="lab API tabs example">
            <Tab label="모임" value="meeting" sx={{ minWidth: "50%" }} />
            <Tab label="클럽" value="club" sx={{ minWidth: "50%" }} />
          </TabList>
        </Box>
        <Box
          sx={{
            overflowY: 'auto',
            maxHeight: 'calc(100vh - 64px)',
          }}
        >
          <TabPanel 
          value="meeting"
          sx={{padding:'0'}}>
            <ListMeeting />
          </TabPanel>

          <TabPanel value="club">
            <ListClub />
          </TabPanel>
        </Box>
      </TabContext>
    </Box>
  );
}
