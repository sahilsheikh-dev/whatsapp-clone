import React from "react";
import StatusUserCard from "./StatusUserCard";
import { AiOutlineClose } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const Status = () => {
  const navigate = useNavigate();
  const { auth, chat, message } = useSelector((store) => store);

  const user = [
    {
      profile_picture:
        "https://images.pexels.com/photos/28970127/pexels-photo-28970127/free-photo-of-young-man-exercising-with-resistance-bands-outdoors.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
      full_name: "Zarlord",
    },
    {
      profile_picture:
        "https://images.pexels.com/photos/399772/pexels-photo-399772.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
      full_name: "Ash Mr. B",
    },
    {
      profile_picture:
        "https://images.pexels.com/photos/18863517/pexels-photo-18863517/free-photo-of-silhouette-of-a-woman-by-the-tracks-during-sunset.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
      full_name: "Disha",
    },
  ];

  return (
    <div>
      <div className="w-100vh flex items-center">
        <div className="h-[100vh] bg-white lg:w-[30%] w-[50%] px-5">
          <div className="pt-5 h-[13%]">
            <StatusUserCard user={auth.reqUser} />
          </div>
          <hr />
          <div className=" overflow-y-scroll h-[86%] pt-2">
            {user.map((item) => (
              <StatusUserCard user={item} />
            ))}
            {/* <StatusUserCard /> */}
          </div>
        </div>
        <div className="relative right h-[100vh] lg:w-[70%] w-[50%] bg-[#f0f2f5]">
          <AiOutlineClose
            onClick={() => navigate(-1)}
            className="cursor-pointer absolute top-5 right-10 text-xl"
          />
        </div>
      </div>
    </div>
  );
};

export default Status;
