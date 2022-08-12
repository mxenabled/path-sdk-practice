class AccountsController < ApplicationController
  def index
    render :json => [
      {
        :id => "acct-123",
        :desc => "Checking Account",
        :t => "CHECKING"
      }
    ]
  end

  def get
    render :json => {
      :id => "acct-123",
      :desc => "Checking Account",
      :t => "CHECKING"
    }
  end
end
