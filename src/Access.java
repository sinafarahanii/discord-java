public class Access {
    private String role;
    private boolean canCreatChannel;
    private boolean canRemoveChannel;
    private boolean canRemoveUser;
    private boolean canChangeServerName;
    private boolean canLimitAccessToChannel;
    private boolean canLimitUser;
    private boolean canPinMassage;

    public Access(String role, boolean canCreatChannel, boolean canRemoveChannel, boolean canRemoveUser, boolean canChangeServerName, boolean canLimitAccessToChannel, boolean canLimitUser, boolean canPinMassage) {
        this.role = role;
        this.canCreatChannel = canCreatChannel;
        this.canRemoveChannel = canRemoveChannel;
        this.canRemoveUser = canRemoveUser;
        this.canChangeServerName = canChangeServerName;
        this.canLimitAccessToChannel = canLimitAccessToChannel;
        this.canLimitUser = canLimitUser;
        this.canPinMassage = canPinMassage;
    }

    public boolean isCanCreatChannel() {
        return canCreatChannel;
    }

    public boolean isCanRemoveChannel() {
        return canRemoveChannel;
    }

    public boolean isCanRemoveUser() {
        return canRemoveUser;
    }

    public boolean isCanChangeServerName() {
        return canChangeServerName;
    }

    public boolean isCanLimitAccessToChannel() {
        return canLimitAccessToChannel;
    }

    public boolean isCanLimitUser() {
        return canLimitUser;
    }

    public boolean isCanPinMassage() {
        return canPinMassage;
    }

    public void setCanCreatChannel(boolean canCreatChannel) {
        this.canCreatChannel = canCreatChannel;
    }

    public void setCanRemoveChannel(boolean canRemoveChannel) {
        this.canRemoveChannel = canRemoveChannel;
    }

    public void setCanRemoveUser(boolean canRemoveUser) {
        this.canRemoveUser = canRemoveUser;
    }

    public void setCanChangeServerName(boolean canChangeServerName) {
        this.canChangeServerName = canChangeServerName;
    }

    public void setCanLimitAccessToChannel(boolean canLimitAccessToChannel) {
        this.canLimitAccessToChannel = canLimitAccessToChannel;
    }

    public void setCanLimitUser(boolean canLimitUser) {
        this.canLimitUser = canLimitUser;
    }

    public void setCanPinMassage(boolean canPinMassage) {
        this.canPinMassage = canPinMassage;
    }
}
