package com.sca.in_telligent.util.mapper;

import android.net.Uri;

import java.util.List;

/**
 * Created by Marcos Ambrosi on 1/14/19.
 */
public class UriToDataMapper {

    public static SubscribeToCommunityData uriToSubscribeToCommunityRequest(Uri data) {
        try {
            List<String> pathParameters = data.getPathSegments();
            int communityId = Integer.parseInt(pathParameters.get(1));

            int inviteId = -1;

            //We can assume there's an invite id in the path
            if (pathParameters.size() > 2) {
                inviteId = Integer.parseInt(pathParameters.get(2));
            }

            return new SubscribeToCommunityData(communityId, inviteId);
        } catch (Exception e) {
            return null;
        }
    }

    public static class SubscribeToCommunityData {
        private int communityId;
        private int inviteId;

        public SubscribeToCommunityData(int communityId, int inviteId) {
            this.communityId = communityId;
            this.inviteId = inviteId;
        }

        public int getCommunityId() {
            return communityId;
        }

        public int getInviteId() {
            return inviteId;
        }
    }
}
